package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;

public class IndexGroupDataStore implements GroupDataStore {

  private ArrayGroupDataStore arrayStore;

  public IndexGroupDataStore(ArrayGroupDataStore store) {
    this.arrayStore = store;
  }

  @Override
  public <T extends DataStore> void put(String name, T provider) {
    arrayStore.put(name, provider);
  }

  @Override
  public <T extends DataStore> T get(String name) {
    return arrayStore.get(name);
  }

  @Override
  public int size() {
    return arrayStore.size();
  }

  public <T extends DataStore> T get(int index) {
    return (T) arrayStore.getDataStore()[index];
  }

  public <T extends DataStore> void put(int index, T store){
    arrayStore.getDataStore()[index] = store;
  }

  public Keys keys() {
    return new Keys(arrayStore.indices());
  }
}
