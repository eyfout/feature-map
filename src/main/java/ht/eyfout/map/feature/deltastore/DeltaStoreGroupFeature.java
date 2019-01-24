package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage.MapGroupDataStorageBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import java.util.Objects;

public class DeltaStoreGroupFeature extends GroupFeature {
  private final DataStorageBuilderFactory dsFactory;

  public DeltaStoreGroupFeature(DataStorageBuilderFactory dsFactory, GroupFeature groupFeature) {
    super(groupFeature);
    this.dsFactory = dsFactory;
  }

  @Override
  public <T> T putScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStorage provider = context.<MapGroupDataStorage>data(this);
    provider.put(name, value);
    throw new StoppedFeatureChainException(this, name, value);
  }

  @Override
  public <T> T getScalarValue(String name, T value, Group element, RuntimeContext context) {
    MapGroupDataStorage store = context.<MapGroupDataStorage>data(this);
    T result = store.get(name);
    if (Objects.isNull(result)) {
      return super.getScalarValue(name, value, element, context);
    }
    return result;
  }

  @Override
  public Object init(RuntimeContext context) {
    return dsFactory.create(MapGroupDataStorageBuilder.class).build();
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
