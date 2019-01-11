package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.data.storage.map.MapGroupDataMart;
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
  private final DataMartFactory dsFactory;

  public DeltaStoreGroupFeature(
      FeatureElementMapFactory elementMapFactory,
      DataMartFactory dsFactory,
      GroupFeature groupFeature) {
    super(groupFeature);
    this.elementMapFactory = elementMapFactory;
    this.dsFactory = dsFactory;
  }

  @Override
  public <T> T putScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataMart provider = context.<MapGroupDataMart>data(this);
    provider.put(name, provider.createScalarProvider(value));
    throw new StoppedFeatureChainException(this, name, value);
  }

  @Override
  public <T> T getScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataMart store = context.<MapGroupDataMart>data(this);
    final ScalarMart<T> scalarProvider = store.<ScalarMart<T>>get(name);
    if (null == scalarProvider) {
      return super.getScalarValue(name, value, element, context);
    }
    T result = scalarProvider.get();
    return (null == result) ? super.getScalarValue(name, value, element, context) : result;
  }

  @Override
  public <T> Scalar<T> getScalar(
      String name, Scalar<T> scalar, Group element, RuntimeContext context) {
    MapGroupDataMart store = context.data(this);
    return elementMapFactory.scalar(element, store.<ScalarMart<T>>get(name), name);
  }

  @Override
  public Object init(RuntimeContext context) {
    return dsFactory.create(MapGroupDataMart.class).build();
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.DELTA_STORE);
  }

  @Override
  public DeltaStoreOperations operations(RuntimeContext context) {
    return new DeltaStoreOperations(context.<MapGroupDataMart>data(this));
  }
}
