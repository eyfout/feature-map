package ht.eyfout.map.data.storage.deltastore;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;

public class DeltaStoreGroupDataStore extends MapGroupDataStore {

  private GroupDataStore baseProvider;

  private DeltaStoreGroupDataStore(GroupDataStore baseProvider) {
    this.baseProvider = baseProvider;
  }

  public static GroupDataStore create(GroupDataStore baseProvider) {
    return new DeltaStoreGroupDataStore(baseProvider);
  }

  @Override
  public <T extends DataStore> T get(String name) {
    T result = super.get(name);
    if (null == result) {
      result = baseProvider.get(name);
    }
    return result;
  }
}
