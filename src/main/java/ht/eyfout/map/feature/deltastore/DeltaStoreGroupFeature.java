package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.features.FeatureProfile;
import ht.eyfout.map.features.GroupFeature;
import ht.eyfout.map.element.internal.RuntimeContext;

public class DeltaStoreGroupFeature extends GroupFeature {

  public DeltaStoreGroupFeature(GroupFeature groupFeature) {
    super(groupFeature);
  }

  @Override
  public <T> T putScalarValue(String name, T value, RuntimeContext context) {
    MapGroupDataStore provider = context.<MapGroupDataStore>data(this);
    provider.put(name, provider.createScalarProvider(value));
    throw new StoppedFeatureChainException(this);
  }

  @Override
  public <T> T getScalarValue(String name, T value, RuntimeContext context) {
    MapGroupDataStore provider = context.<MapGroupDataStore>data(this);
    final ScalarStore<T> scalarProvider = provider.<ScalarStore<T>>get(name);
    if( null == scalarProvider){
      return super.getScalarValue(name, value, context);
    }
    T result = scalarProvider.get();
    return (null == result) ? super.getScalarValue(name, value, context) : result;
  }

  @Override
  public Object init(RuntimeContext context) {
    return new MapGroupDataStore();
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.DELTA_STORE);
  }
}
