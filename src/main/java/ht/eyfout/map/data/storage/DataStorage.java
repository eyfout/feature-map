package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;

public interface DataStorage {
  default boolean isImmutable() {
    return false;
  }

  default <T extends DataStorage> T copy() {
    throw new UnsupportedOperationException();
  }


  default <T> T accept(DataStorageVisitor visitor){
    throw new UnsupportedOperationException();
  }

  int size();

  @FunctionalInterface
  interface DataStorageBuilder<R extends GroupDataStorage> {
    R build();
  }
}
