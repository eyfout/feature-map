package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;

public class IndexGroupDataStorage implements GroupDataStorage {

  private ArrayGroupDataStorage arrayStore;

  IndexGroupDataStorage(ArrayGroupDataStorage store) {
    this.arrayStore = store;
  }

  @Override
  public <T extends DataStorage> void putAsDataStore(String name, T dataStore) {
    arrayStore.putAsDataStore(name, dataStore);
  }

  @Override
  public <T> void put(String name, T value) {
    arrayStore.put(name, value);
  }

  @Override
  public <T extends DataStorage> T getAsDataStore(String name) {
    return arrayStore.getAsDataStore(name);
  }

  @Override
  public <T> T get(String name) {
    return arrayStore.get(name);
  }

  @Override
  public int size() {
    return arrayStore.size();
  }

  public <T extends DataStorage> T getAsDataStorage(int index) {
    return get(index);
  }

  public <T> T get(int index) {
    return arrayStore.getByIndex(index);
  }

  public <T extends DataStorage> void put(int index, T store) {
    arrayStore.getDataStorage()[index] = store;
  }

  public Keys keys() {
    return new Keys(arrayStore.getNameFunc(), arrayStore.getIndexFunc());
  }
}
