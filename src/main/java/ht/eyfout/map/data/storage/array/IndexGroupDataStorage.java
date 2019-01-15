package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;

public class IndexGroupDataStorage implements GroupDataStorage {

  private ArrayGroupDataStorage arrayStore;

  IndexGroupDataStorage(ArrayGroupDataStorage store) {
    this.arrayStore = store;
  }

  @Override
  public <T extends DataStorage> void put(String name, T provider) {
    arrayStore.put(name, provider);
  }

  @Override
  public <T extends DataStorage> T get(String name) {
    return arrayStore.get(name);
  }

  @Override
  public int size() {
    return arrayStore.size();
  }

  public <T extends DataStorage> T get(int index) {
    return (T) arrayStore.getByIndex(index);
  }

  public <T extends DataStorage> void put(int index, T store) {
    arrayStore.getDataMart()[index] = store;
  }

  public Keys keys() {
    return new Keys(arrayStore.getNameFunc(), arrayStore.getIndexFunc());
  }
}
