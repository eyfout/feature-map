package ht.eyfout.map.data.storage;

public interface GroupDataStore extends DataStore {
  <T extends DataStore> void put(String name, T provider);

  <T extends DataStore> T get(String name);

  default <T> ScalarStore<T> createScalarProvider() {
    return createScalarProvider(null);
  }

  default <T> ScalarStore<T> createScalarProvider(T initialValue) {
    return new ScalarStore<T>(initialValue);
  }
}
