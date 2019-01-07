package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;
import java.util.Arrays;

public class ArrayGroupDataStore implements GroupDataStore {
  private static final DataStore[] EMPTY_STORE = new DataStore[0];
  DataStore[] store = EMPTY_STORE;
  DataStore[] store2 = EMPTY_STORE;

  @Override
  public <T extends DataStore> void put(String name, T provider) {
    getDataStore(name)[Math.abs(name.hashCode())] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStore> T get(String name) {
    return (T) getDataStore(name)[Math.abs(name.hashCode())];
  }

  @Override
  public int size() {
    return 0;
  }

  DataStore[] getDataStore(String name){
    int hashCode = name.hashCode();
    if(1 > hashCode ){
      store = expandStorage(store, hashCode);
      return store;
    } else {
      store2 = expandStorage(store2, Math.abs(hashCode));
      return store2;
    }
  }

  private DataStore[] expandStorage(DataStore[] arr, int size) {
      return Arrays.copyOf(arr, size + 1);
  }
}
