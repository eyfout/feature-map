package ht.eyfout.map.data.storage;

public interface DataStore {
  default boolean isImmutable() {
    return false;
  }

  int size();
}
