package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public interface DataStoreFactory {
  <T extends DataStore> DataStoreBuilder<T> create( Class<T> clazz );
}
