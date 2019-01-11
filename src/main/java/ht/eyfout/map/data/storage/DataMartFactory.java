package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataMart.DataStoreBuilder;

public interface DataMartFactory {
  <T extends GroupDataMart, B extends DataStoreBuilder<T>> B create(Class<T> clazz);
}
