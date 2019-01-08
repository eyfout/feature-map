package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.FeatureElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;

public class DeltaStoreGroupFeature extends GroupFeature {
  private final FeatureElementMapFactory elementMapFactory;

  public DeltaStoreGroupFeature(
      FeatureElementMapFactory elementMapFactory, GroupFeature groupFeature) {
    super(groupFeature);
    this.elementMapFactory = elementMapFactory;
  }

  @Override
  public <T> T putScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStore provider = context.<MapGroupDataStore>data(this);
    provider.put(name, provider.createScalarProvider(value));
    throw new StoppedFeatureChainException(this, name, value);
  }

  @Override
  public <T> T getScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStore store = context.<MapGroupDataStore>data(this);
    final ScalarStore<T> scalarProvider = store.<ScalarStore<T>>get(name);
    if (null == scalarProvider) {
      return super.getScalarValue(name, value, element, context);
    }
    T result = scalarProvider.get();
    return (null == result) ? super.getScalarValue(name, value, element, context) : result;
  }

  @Override
  public <T> Scalar<T> getScalar(
      String name, Scalar<T> scalar, Group element, RuntimeContext context) {
    MapGroupDataStore store = context.data(this);
    return elementMapFactory.scalar(element, store.<ScalarStore<T>>get(name), name);
  }

  @Override
  public Object init(RuntimeContext context) {
    return new MapGroupDataStore();
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.DELTA_STORE);
  }

  @Override
  public DeltaStoreRuntime runtimeData(RuntimeContext context) {
    return new DeltaStoreRuntime(context.<MapGroupDataStore>data(this));
  }
}
