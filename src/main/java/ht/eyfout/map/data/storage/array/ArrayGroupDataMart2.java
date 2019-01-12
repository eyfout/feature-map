package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArrayGroupDataMart2 extends ArrayGroupDataMart {

  Map<String, ArrayEntry> indices;
  Map<Integer, ArrayEntry> indicesInt;

  ArrayGroupDataMart2() {
    indices = new HashMap<>();
    indicesInt = new HashMap<>();
  }

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    int indx = getIndex(name);
    getDataMart()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataMart> T get(String name) {
    return (T) getDataMart()[getIndex(name)];
  }

  @Override
  <T extends DataMart> T getByIndex(int index) {
    return getIndicesInt().get(index).getMart();
  }

  @Override
  Function<Integer, String> getNameFunc() {
    return (it) -> {
      for (Map.Entry<String, ArrayEntry> entry : this.getIndices().entrySet()) {
        if (it.equals(entry.getValue().index())) {
          return entry.getKey();
        }
      }
      throw new IllegalArgumentException();
    };
  }

  @Override
  Function<String, Integer> getIndexFunc() {
    return (it) -> {
      ArrayEntry entry = this.getIndices().get(it);
      if (null == entry) {
        throw new IllegalArgumentException();
      }
      return entry.index();
    };
  }

  protected DataMart[] getDataMart() {
    return store;
  }

  private Map<String, ArrayEntry> getIndices() {
    if (null == indices) {
      indices = new HashMap<>();
    }
    return indices;
  }

  private Map<Integer, ArrayEntry> getIndicesInt() {
    if (null == indicesInt) {
      indicesInt = new HashMap<>();
    }
    return indicesInt;
  }

  private int getIndex(String name) {
    ArrayEntry entry = getIndices().get(name);
    if (null == entry) {
      size++;
      entry = new ArrayEntry(nextIndex++, this);
      getIndices().put(name, entry);
      getIndicesInt().put(entry.index(), entry);
    }

    if (store.length <= entry.index()) {
      store =
          expandStorage(
              store, (store.length == 0) ? INITIAL_SIZE : store.length + (INITIAL_SIZE << 1));
    }
    return entry.index();
  }

  protected DataMart[] expandStorage(DataMart[] arr, int newSize) {
    return Arrays.copyOf(arr, newSize);
  }

  @Override
  public ArrayGroupDataMart2 copy() {
    return new ArrayGroupDataMart2Copy(this);
  }
}
