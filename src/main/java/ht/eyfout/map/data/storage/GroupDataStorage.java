package ht.eyfout.map.data.storage;

public interface GroupDataStorage extends DataStorage {

  /**
   * Stores a {@link DataStorage} with reference specified
   *
   * @param name
   * @param dataStore
   * @param <T>
   */
  <T extends DataStorage> void putAsDataStore(String name, T dataStore);

  /**
   * Places any value / Object in the data store
   *
   * @param name reference to value being stored
   * @param value value to store
   * @param <T> value type
   */
  <T> void put(String name, T value);

  /**
   * Retrieves the {@link DataStorage} in this store with the reference otherwise return null.
   *
   * @param <T>
   * @param name
   * @return
   */
  <T extends DataStorage> T getAsDataStore(String name);

  /**
   * Returns the value with the reference otherwise null.
   *
   * @param name
   * @param <T>
   * @return
   */
  <T> T get(String name);
}
