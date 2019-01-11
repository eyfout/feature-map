package ht.eyfout.map.data.storage;

public interface DataMart {
  default boolean isImmutable() {
    return false;
  }

  int size();

  @FunctionalInterface
  interface DataStoreBuilder<R extends GroupDataMart> {
    R build();
  }
}
