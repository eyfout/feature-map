package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapGroupDataStore implements GroupDataStore {

  public MapGroupDataStore() {
    //
  }

  private Map<String, DataStore> store = new ConcurrentHashMap<>();

  @Override
  public <T extends DataStore> void put(String name, T provider) {
   store.put(name, provider);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStore> T get(String name) {
    return (T)store.get(name);
  }

  @Override
  public int size() {
    return store.size();
  }
}
