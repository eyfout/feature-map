package ht.eyfout.map.data.storage;

import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;

public interface DataStorage {
  default boolean isImmutable() {
    return false;
  }

  default <T extends DataStorage> T copy() {
    throw new UnsupportedOperationException();
  }

  default <R> R accept(DataStorageVisitor<R> visitor) {
    throw new UnsupportedOperationException();
  }

  int size();

  @FunctionalInterface
  interface DataStorageBuilder<R extends GroupDataStorage> {
    R build();
  }
}
