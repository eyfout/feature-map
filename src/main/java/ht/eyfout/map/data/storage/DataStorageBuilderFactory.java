package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;

public interface DataStorageBuilderFactory {
  <T extends GroupDataStorage, B extends DataStorageBuilder<T>> B create(Class<T> clazz);
}
