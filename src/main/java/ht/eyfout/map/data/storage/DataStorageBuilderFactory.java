package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;

public interface DataStorageBuilderFactory {
  < B extends DataStorageBuilder> B create(Class<B> clazz);
}
