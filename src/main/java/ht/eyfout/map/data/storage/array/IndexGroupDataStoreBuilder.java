package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public class IndexGroupDataStoreBuilder implements DataStoreBuilder<IndexGroupDataStore> {

  public IndexGroupDataStoreBuilder() {
  }

  public IndexGroupDataStore array(ArrayGroupDataStore store){
    return new IndexGroupDataStore(store);
  }

  @Override
  public IndexGroupDataStore build() {
      throw new IllegalStateException();
  }
}
