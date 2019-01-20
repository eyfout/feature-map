package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;
import ht.eyfout.map.data.storage.visitor.VisitorResult;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ArrayGroupDataStorage2 extends ArrayGroupDataStorage {

  Map<String, ArrayEntry> indices;
  Map<Integer, ArrayEntry> indicesInt;

  ArrayGroupDataStorage2() {
    indices = new HashMap<>();
    indicesInt = new HashMap<>();
  }

  @Override
  public <T extends DataStorage> void put(String name, T provider) {
    int indx = getIndex(name);
    getDatastorage()[indx] = provider;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T get(String name) {
    ArrayEntry entry = indices.get(name);
    if (null == entry) {
      return null;
    }
    return entry.getstorage();
  }

  @Override
  <T extends DataStorage> T getByIndex(int index) {
    return getIndicesInt().get(index).getstorage();
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

  protected DataStorage[] getDatastorage() {
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

  protected DataStorage[] expandStorage(DataStorage[] arr, int newSize) {
    return Arrays.copyOf(arr, newSize);
  }

  @Override
  public ArrayGroupDataStorage2 copy() {
    return new ArrayGroupDataStorage2Copy(this);
  }

  @Override
  public <T> T accept(DataStorageVisitor visitor) {
    VisitorResult progress;
    visitor.pre(this);
    for (Map.Entry<String, ArrayEntry> entry : indices.entrySet()) {
      progress = visitor.visit(entry.getKey(), entry.getValue().getstorage());
      if (progress == VisitorResult.HALT) {
        break;
      }
    }
    visitor.post(this);
    return visitor.result();
  }
}
