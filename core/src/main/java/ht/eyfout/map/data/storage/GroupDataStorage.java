package ht.eyfout.map.data.storage;

public interface GroupDataStorage extends DataStorage {
  <T extends DataStorage> void put(String name, T provider);

  <T extends DataStorage> T get(String name);

  default <T> ScalarDataStorage<T> createScalarProvider() {
    return createScalarProvider(null);
  }

  default <T> ScalarDataStorage<T> createScalarProvider(T initialValue) {
    return new ScalarDataStorage<T>(initialValue);
  }
}
