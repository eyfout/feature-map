package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import javax.inject.Inject;

public class ArrayGroupDataStoreBuilder implements DataStoreBuilder<ArrayGroupDataStore> {

  @Inject
  public ArrayGroupDataStoreBuilder() {}

  @Override
  public ArrayGroupDataStore build() {
    return new ArrayGroupDataStore();
  }
}
