package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

public class MapGroupDataStorage implements GroupDataStorage {

  private Map<String, Object> store = new ConcurrentHashMap<>();

  protected MapGroupDataStorage() {
    //
  }

  @Override
  public <T extends DataStorage> void putAsDataStore(String name, T dataStore) {
    store.put(name, dataStore);
  }

  @Override
  public <T> void put(String name, T value) {
    store.put(name, value);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T getAsDataStore(String name) {
    return (T) store.get(name);
  }

  @Override
  public <T> T get(String name) {
    return (T) store.get(name);
  }

  @Override
  public int size() {
    return store.size();
  }

  public static class MapGroupDataStorageBuilder
      implements DataStorageBuilder<MapGroupDataStorage> {

    @Inject
    protected MapGroupDataStorageBuilder() {
      //
    }

    @Override
    public MapGroupDataStorage build() {
      return new MapGroupDataStorage();
    }
  }
}
