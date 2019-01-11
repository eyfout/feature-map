package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public interface DataStoreFactory {
  <T extends GroupDataStore, B extends DataStoreBuilder<T>> B create(Class<T> clazz);
}
