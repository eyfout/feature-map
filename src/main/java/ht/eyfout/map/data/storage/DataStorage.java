package ht.eyfout.map.data.storage;

public interface DataStorage {
  default boolean isImmutable() {
    return false;
  }

  default <T extends DataStorage> T copy(){
    throw new UnsupportedOperationException();
  }

  int size();

  @FunctionalInterface
  interface DataStorageBuilder<R extends GroupDataStorage> {
    R build();
  }
}
