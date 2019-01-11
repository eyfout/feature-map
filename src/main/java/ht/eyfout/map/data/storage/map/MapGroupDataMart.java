package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

public class MapGroupDataMart implements GroupDataMart {

  private Map<String, DataMart> store = new ConcurrentHashMap<>();

  protected MapGroupDataMart() {
    //
  }

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    store.put(name, provider);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataMart> T get(String name) {
    return (T) store.get(name);
  }

  @Override
  public int size() {
    return store.size();
  }

  public static class MapGroupDataStoreBuilder implements DataStoreBuilder<MapGroupDataMart> {

    @Inject
    protected MapGroupDataStoreBuilder() {
      //
    }

    @Override
    public MapGroupDataMart build() {
      return new MapGroupDataMart();
    }
  }
}
