package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;

public class ArrayGroupDataStorage implements GroupDataStorage {
  static final int INITIAL_SIZE = 6;
  private static final DataStorage[] EMPTY_STORE = new DataStorage[0];
  protected DataStorage[] store = EMPTY_STORE;
  int nextIndex = 0;
  int size = 0;
  private Map<String, Integer> indices;

  ArrayGroupDataStorage(){
    indices = new HashMap<>();
  }

  private ArrayGroupDataStorage(ArrayGroupDataStorage mart){
    indices = new HashMap<>(mart.indices);
    store = Arrays.copyOf(mart.store, mart.store.length);
    nextIndex = mart.size;
  }

  @Override
  public <T extends DataStorage> void put(String name, T provider) {
    size++;
    int indx = getIndex(name);
    getDataMart()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T get(String name) {
    Integer index = indices.get(name);
    if(null == index){
      return null;
    }
    return (T)getDataMart()[index];
  }

  @Override
  public int size() {
    return size;
  }

  DataStorage[] getDataMart() {
    return store;
  }

  private int getIndex(String name) {
    Integer i = indices.get(name);
    if (null == i) {
      i = nextIndex;
      indices.put(name, nextIndex++);
      if (store.length <= nextIndex) {
        store = expandStorage(store);
      }
    }
    return i;
  }

  <T extends DataStorage> T getByIndex(int index) {
    return (T) getDataMart()[index];
  }

  private DataStorage[] expandStorage(DataStorage[] arr) {
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
  public <T extends DataStorage> T copy() {
    return (T)new ArrayGroupDataStorage(this);
  }

  Function<String, Integer> getIndexFunc() {
    return (s) -> indices.get(s);
  }

  public static class ArrayGroupDataStorageBuilder implements
      DataStorageBuilder<ArrayGroupDataStorage> {

    ArrayGroupVersion version = ArrayGroupVersion.FUNCTION;

    @Inject
    public ArrayGroupDataStorageBuilder() {}

    public ArrayGroupDataStorageBuilder version(ArrayGroupVersion version) {
      this.version = version;
      return this;
    }

    public IndexGroupDataStorage index(ArrayGroupDataStorage arr){
      return new IndexGroupDataStorage(arr);
    }

    @Override
    public ArrayGroupDataStorage build() {
      switch (version) {
        case INT:
          return new ArrayGroupDataStorage();
        default:
        case FUNCTION:
          return new ArrayGroupDataStorage2();
      }
    }

    public enum ArrayGroupVersion {
      FUNCTION,
      INT
    }
  }
}
