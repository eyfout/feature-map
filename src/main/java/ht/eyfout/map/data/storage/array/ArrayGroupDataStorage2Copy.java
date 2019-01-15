package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarStorage;
import java.util.HashMap;

public class ArrayGroupDataStorage2Copy extends ArrayGroupDataStorage2 {

  private boolean hasLocalChanges;

  ArrayGroupDataStorage2Copy(ArrayGroupDataStorage2 arr) {
    indices = arr.indices;
    indicesInt = arr.indicesInt;
  }

  @Override
  public <T extends DataStorage> void put(String name, T provider) {
    modifying(name);
    super.put(name, provider);
  }

  @Override
  public <T extends DataStorage> T get(String name) {
    ArrayEntry entry = indices.get(name);
    T result = entry.getMart();
    if (isRemoteEntry(entry) && null != result) {
      if (ScalarStorage.class.isAssignableFrom((result.getClass()))) {
        return (T) new ScalarEntryStorage<>(name, entry);
      }
      throw new UnsupportedOperationException();
    }
    return result;
  }

  private void modifying(String name) {
    if (!hasLocalChanges) {
      hasLocalChanges = true;
      indices = new HashMap<>(indices);
      indicesInt = new HashMap<>(indicesInt);
      nextIndex = indices.size();
    }

    ArrayEntry entry = indices.get(name);
    if (isRemoteEntry(entry)) {
      entry = new ArrayEntry(entry.index(), this);
      indices.put(name, entry);
      indicesInt.put(entry.index(), entry);
    }
  }

  protected boolean isRemoteEntry(ArrayEntry entry) {
    if (null != entry && !entry.source().equals(this)) {
      return true;
    }
    return false;
  }

  class GroupEntryStorage implements GroupDataStorage {

    ArrayEntry entry;
    String name;

    public GroupEntryStorage(String name, ArrayEntry entry) {
      this.name = name;
      this.entry = entry;
    }

    @Override
    public <T extends DataStorage> void put(String name, T provider) {
      entry.getMart();
    }

    @Override
    public <T extends DataStorage> T get(String name) {
      return entry.<GroupDataStorage>getMart().get(name);
    }

    @Override
    public int size() {
      return 0;
    }
  }

  class ScalarEntryStorage<T> extends ScalarStorage<T> {
    ArrayEntry entry;
    String name;
    boolean created;

    public ScalarEntryStorage(String name, ArrayEntry entry) {
      this.entry = entry;
      this.name = name;
    }

    @Override
    public T get() {
      return entry.<ScalarStorage<T>>getMart().get();
    }

    @Override
    public void set(T value) {
      if (!isRemoteEntry(entry)) {
        entry.<ScalarStorage<T>>getMart().set(value);
      } else {
        put(name, new ScalarStorage<>(value));
        entry = indices.get(name);
      }
      created = true;
    }
  }
}
