package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.GroupDataStore;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class ArrayGroupDataStore implements GroupDataStore {
  private static final DataStore[] EMPTY_STORE = new DataStore[0];
  private static int INITIAL_SIZE = 16;
  private int number = 0;
  private DataStore[] store = EMPTY_STORE;
  private Map<String, Integer> indices = new HashMap<>();

  @Override
  public <T extends DataStore> void put(String name, T provider) {
    int indx = getIndex(name);
    getDataStore()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStore> T get(String name) {
    return (T) getDataStore()[getIndex(name)];
  }

  @Override
  public int size() {
    return 0;
  }

  Map<String, Integer> indices() {
    return indices;
  }

  DataStore[] getDataStore() {
    return store;
  }

  private int getIndex(String name) {
    Integer i = indices.get(name);
    if (null == i) {
      i = number;
      indices.put(name, number++);
      if (store.length < number) {
        store = expandStorage(store);
      }
    }
    return i;
  }

  private DataStore[] expandStorage(DataStore[] arr) {
    return Arrays.copyOf(arr, arr.length + INITIAL_SIZE);
  }

  public static class ArrayGroupDataStoreBuilder implements DataStoreBuilder<ArrayGroupDataStore> {

    @Inject
    public ArrayGroupDataStoreBuilder() {}

    @Override
    public ArrayGroupDataStore build() {
      return new ArrayGroupDataStore();
    }
  }
}
