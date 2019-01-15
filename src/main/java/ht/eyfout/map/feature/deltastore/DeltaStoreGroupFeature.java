package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.ScalarStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.factory.FeatureElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;

public class DeltaStoreGroupFeature extends GroupFeature {
  private final FeatureElementMapFactory elementMapFactory;
  private final DataStorageBuilderFactory dsFactory;

  public DeltaStoreGroupFeature(
      FeatureElementMapFactory elementMapFactory,
      DataStorageBuilderFactory dsFactory,
      GroupFeature groupFeature) {
    super(groupFeature);
    this.elementMapFactory = elementMapFactory;
    this.dsFactory = dsFactory;
  }

  @Override
  public <T> T putScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStorage provider = context.<MapGroupDataStorage>data(this);
    provider.put(name, provider.createScalarProvider(value));
    throw new StoppedFeatureChainException(this, name, value);
  }

  @Override
  public <T> T getScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStorage store = context.<MapGroupDataStorage>data(this);
    final ScalarStorage<T> scalarProvider = store.<ScalarStorage<T>>get(name);
    if (null == scalarProvider) {
      return super.getScalarValue(name, value, element, context);
    }
    T result = scalarProvider.get();
    return (null == result) ? super.getScalarValue(name, value, element, context) : result;
  }

  @Override
  public <T> ScalarStorage<T> getScalar(
      String name, ScalarStorage<T> scalar, Group element, RuntimeContext context) {
    MapGroupDataStorage store = context.data(this);
    ScalarStorage<T> result = store.get(name);
    if (null == result) {
      return scalar;
    }
    return result;
  }

  @Override
  public Object init(RuntimeContext context) {
    return dsFactory.create(MapGroupDataStorage.class).build();
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.DELTA_STORE);
  }

  @Override
  public DeltaStoreOperations operations(RuntimeContext context) {
    return new DeltaStoreOperations(context.<MapGroupDataStorage>data(this));
  }
}
