package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.StorageBuilder;
import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.inject.Inject;

public class ArrayGroupDataStorage implements GroupDataStorage {
  static final int INITIAL_SIZE = 6;
  private static final Object[] EMPTY_STORE = new Object[0];
  protected Object[] store = EMPTY_STORE;
  int nextIndex = 0;
  private Map<String, Integer> indices;

  ArrayGroupDataStorage() {
    indices = new HashMap<>();
  }

  private ArrayGroupDataStorage(ArrayGroupDataStorage storage) {
    indices = new HashMap<>(storage.indices);
    store = Arrays.copyOf(storage.store, storage.store.length);
    nextIndex = indices.size();
  }

  @Override
  public <T extends DataStorage> void putAsDataStore(String name, T dataStore) {
    put(name, dataStore);
  }

  @Override
  public <T> void put(String name, T value) {
    int index = getIndex(name);
    getDataStorage()[index] = value;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T getAsDataStore(String name) {
    return get(name);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T get(String name) {
    Integer index = indices.get(name);
    if (null == index) {
      return null;
    }
    return (T) getDataStorage()[index];
  }

  @Override
  public int size() {
    return indices.size();
  }

  Object[] getDataStorage() {
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

  @SuppressWarnings("unchecked")
  <T> T getByIndex(int index) {
    return (T) getDataStorage()[index];
  }

  private Object[] expandStorage(Object[] arr) {
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
    return (T) new ArrayGroupDataStorage(this);
  }

  Function<String, Integer> getIndexFunc() {
    return (s) -> indices.get(s);
  }

  @StorageBuilder
  public static class ArrayGroupDataStorageBuilder
      implements DataStorageBuilder<ArrayGroupDataStorage> {

    ArrayGroupVersion version = ArrayGroupVersion.FUNCTION;

    @Inject
    public ArrayGroupDataStorageBuilder() {}

    public ArrayGroupDataStorageBuilder version(ArrayGroupVersion version) {
      this.version = version;
      return this;
    }

    public IndexGroupDataStorage index(ArrayGroupDataStorage arr) {
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
