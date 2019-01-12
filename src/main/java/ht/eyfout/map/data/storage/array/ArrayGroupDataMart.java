package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;

public class ArrayGroupDataMart implements GroupDataMart {
  private static final DataMart[] EMPTY_STORE = new DataMart[0];
  static final int INITIAL_SIZE = 6;
  protected DataMart[] store = EMPTY_STORE;
  private Map<String, Integer> indices = new HashMap<>();
  int nextIndex = 0;
  int size = 0;

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    size++;
    int indx = getIndex(name);
    getDataMart()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataMart> T get(String name) {
    return (T) getDataMart()[getIndex(name)];
  }

  @Override
  public int size() {
    return size;
  }

  DataMart[] getDataMart() {
    return store;
  }

  private int getIndex(String name) {
    Integer i = indices.get(name);
    if (null == i) {
      i = nextIndex;
      indices.put(name, nextIndex++);
      if (store.length < nextIndex) {
        store = expandStorage(store);
      }
    }
    return i;
  }

  <T extends DataMart> T getByIndex(int index){
    return (T)getDataMart()[index];
  }

  private DataMart[] expandStorage(DataMart[] arr) {
    return Arrays.copyOf(arr, arr.length + INITIAL_SIZE);
  }

  Function<Integer, String> getNameFunc() {
    return (it) -> {
      for (Map.Entry<String, Integer> entry : indices.entrySet()) {
        if (it.equals(entry.getValue())) {
          return entry.getKey();
        }
      }
      throw new IllegalArgumentException();
    };
  }

  Function<String, Integer> getIndexFunc() {
    return (s) -> indices.get(s);
  }

  public static class ArrayGroupDataMartBuilder implements DataMartBuilder<ArrayGroupDataMart> {

    @Inject
    public ArrayGroupDataMartBuilder() {}

    @Override
    public ArrayGroupDataMart build() {
      return new ArrayGroupDataMart2();
    }
  }
}
