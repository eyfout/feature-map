package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public class IndexGroupDataStoreBuilder implements DataStoreBuilder<IndexGroupDataStore> {
  private ArrayGroupDataStore store;

  public IndexGroupDataStoreBuilder() {
  }

  public IndexGroupDataStoreBuilder array(ArrayGroupDataStore store){
    this.store = store;
    return this;
  }

  @Override
  public IndexGroupDataStore build() {
    if (null == store) {
      throw new IllegalStateException();
    }
    return new IndexGroupDataStore(store);
  }
}
