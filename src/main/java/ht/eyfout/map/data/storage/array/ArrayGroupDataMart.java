package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class ArrayGroupDataMart implements GroupDataMart {
  private static final DataMart[] EMPTY_STORE = new DataMart[0];
  private static int INITIAL_SIZE = 16;
  private int number = 0;
  private DataMart[] store = EMPTY_STORE;
  private Map<String, Integer> indices = new HashMap<>();

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    int indx = getIndex(name);
    getDataStore()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataMart> T get(String name) {
    return (T) getDataStore()[getIndex(name)];
  }

  @Override
  public int size() {
    return 0;
  }

  Map<String, Integer> indices() {
    return indices;
  }

  DataMart[] getDataStore() {
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

  private DataMart[] expandStorage(DataMart[] arr) {
    return Arrays.copyOf(arr, arr.length + INITIAL_SIZE);
  }

  public static class ArrayGroupDataStoreBuilder implements DataStoreBuilder<ArrayGroupDataMart> {

    @Inject
    public ArrayGroupDataStoreBuilder() {}

    @Override
    public ArrayGroupDataMart build() {
      return new ArrayGroupDataMart();
    }
  }
}
