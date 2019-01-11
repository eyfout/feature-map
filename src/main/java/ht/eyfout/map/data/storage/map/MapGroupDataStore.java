package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

public class MapGroupDataStore implements GroupDataStore {

  private Map<String, DataStore> store = new ConcurrentHashMap<>();

  protected MapGroupDataStore() {
    //
  }

  @Override
  public <T extends DataStore> void put(String name, T provider) {
    store.put(name, provider);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStore> T get(String name) {
    return (T) store.get(name);
  }

  @Override
  public int size() {
    return store.size();
  }

  public static class MapGroupDataStoreBuilder implements DataStoreBuilder<MapGroupDataStore> {

    @Inject
    protected MapGroupDataStoreBuilder() {
      //
    }

    @Override
    public MapGroupDataStore build() {
      return new MapGroupDataStore();
    }
  }
}
