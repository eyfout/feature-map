package ht.eyfout.map.data.storage.deltastore;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage;

public class DeltaStoreGroupDataStorage extends MapGroupDataStorage {

  private GroupDataStorage baseProvider;

  private DeltaStoreGroupDataStorage(GroupDataStorage baseProvider) {
    this.baseProvider = baseProvider;
  }

  public static GroupDataStorage create(GroupDataStorage baseProvider) {
    return new DeltaStoreGroupDataStorage(baseProvider);
  }

  @Override
  public <T extends DataStorage> T getAsDataStore(String name) {
    T result = super.getAsDataStore(name);
    if (null == result) {
      result = baseProvider.getAsDataStore(name);
    }
    return result;
  }

  public static class DeltaStoreGroupDataStorageBuilder
      implements DataStorageBuilder<DeltaStoreGroupDataStorage> {

    public DeltaStoreGroupDataStorage group(GroupDataStorage group) {
      return new DeltaStoreGroupDataStorage(group);
    }

    @Override
    public DeltaStoreGroupDataStorage build() {
      return null;
    }
  }
}
