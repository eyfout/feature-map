package ht.eyfout.map.data.storage;

public interface DataMart {
  default boolean isImmutable() {
    return false;
  }

  default <T extends DataMart> T copy(){
    throw new UnsupportedOperationException();
  }

  int size();

  @FunctionalInterface
  interface DataMartBuilder<R extends GroupDataMart> {
    R build();
  }
}
