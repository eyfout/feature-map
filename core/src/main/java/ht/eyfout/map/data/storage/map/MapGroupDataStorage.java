package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;

public class MapGroupDataStorage implements GroupDataStorage {

  private Map<String, DataStorage> store = new ConcurrentHashMap<>();

  protected MapGroupDataStorage() {
    //
  }

  @Override
  public <T extends DataStorage> void put(String name, T provider) {
    store.put(name, provider);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T get(String name) {
    return (T) store.get(name);
  }

  @Override
  public <R> R accept(DataStorageVisitor<R> visitor) {
    visitor.pre(this);
    for(Map.Entry<String, DataStorage> entry : store.entrySet()){
      DataStorage value = entry.getValue();
      if(value instanceof ScalarDataStorage){
        visitor.visit(entry.getKey(), (ScalarDataStorage) entry.getValue());
      }

    }
    return visitor.post(this);
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
