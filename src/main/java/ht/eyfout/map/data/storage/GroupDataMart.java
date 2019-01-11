package ht.eyfout.map.data.storage;

public interface GroupDataMart extends DataMart {
  <T extends DataMart> void put(String name, T provider);

  <T extends DataMart> T get(String name);

  default <T> ScalarMart<T> createScalarProvider() {
    return createScalarProvider(null);
  }

  default <T> ScalarMart<T> createScalarProvider(T initialValue) {
    return new ScalarMart<T>(initialValue);
  }
}
