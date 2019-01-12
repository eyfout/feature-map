package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.DataMart.DataMartBuilder;

public interface DataMartFactory {
  <T extends GroupDataMart, B extends DataMartBuilder<T>> B create(Class<T> clazz);
}
