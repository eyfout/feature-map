package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;

public class ArrayGroupDataMart implements GroupDataMart {
  static final int INITIAL_SIZE = 6;
  private static final DataMart[] EMPTY_STORE = new DataMart[0];
  protected DataMart[] store = EMPTY_STORE;
  int nextIndex = 0;
  int size = 0;
  private Map<String, Integer> indices;

  ArrayGroupDataMart(){
    indices = new HashMap<>();
  }

  private ArrayGroupDataMart(ArrayGroupDataMart mart){
    indices = new HashMap<>(mart.indices);
    store = Arrays.copyOf(mart.store, mart.store.length);
    nextIndex = mart.size;
  }

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

  <T extends DataMart> T getByIndex(int index) {
    return (T) getDataMart()[index];
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

  @Override
  public <T extends DataMart> T copy() {
    return (T)new ArrayGroupDataMart(this);
  }

  Function<String, Integer> getIndexFunc() {
    return (s) -> indices.get(s);
  }

  public static class ArrayGroupDataMartBuilder implements DataMartBuilder<ArrayGroupDataMart> {

    ArrayGroupVersion version = ArrayGroupVersion.FUNCTION;

    @Inject
    public ArrayGroupDataMartBuilder() {}

    public ArrayGroupDataMartBuilder version(ArrayGroupVersion version) {
      this.version = version;
      return this;
    }

    public IndexGroupDataMart index(ArrayGroupDataMart arr){
      return new IndexGroupDataMart(arr);
    }

    @Override
    public ArrayGroupDataMart build() {
      switch (version) {
        case INT:
          return new ArrayGroupDataMart();
        default:
        case FUNCTION:
          return new ArrayGroupDataMart2();
      }
    }

    public enum ArrayGroupVersion {
      FUNCTION,
      INT
    }
  }
}
