package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.ScalarMart;
import java.util.HashMap;

public class ArrayGroupDataMart2Copy extends ArrayGroupDataMart2 {

  private boolean hasLocalChanges;

  ArrayGroupDataMart2Copy(ArrayGroupDataMart2 arr) {
    indices = arr.indices;
    indicesInt = arr.indicesInt;
  }

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    modifying(name);
    super.put(name, provider);
  }

  @Override
  public <T extends DataMart> T get(String name) {
    ArrayEntry entry = indices.get(name);
    T result = super.get(name);
    if (isRemoteEntry(entry)) {
      if (ScalarMart.class.isAssignableFrom((result.getClass()))) {
        result = (T) new ScalarEntryMart<>(name, entry);
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

  boolean isRemoteEntry(ArrayEntry entry) {
    if (null != entry && !entry.source().equals(this)) {
      return true;
    }
    return false;
  }

  class GroupEntryMart implements GroupDataMart {

    ArrayEntry entry;
    String name;

    public GroupEntryMart(String name, ArrayEntry entry) {
      this.name = name;
      this.entry = entry;
    }

    @Override
    public <T extends DataMart> void put(String name, T provider) {
      entry.getMart();
    }

    @Override
    public <T extends DataMart> T get(String name) {
      return entry.<GroupDataMart>getMart().get(name);
    }

    @Override
    public int size() {
      return 0;
    }
  }

  class ScalarEntryMart<T> extends ScalarMart<T> {
    ArrayEntry entry;
    String name;
    boolean created;

    public ScalarEntryMart(String name, ArrayEntry entry) {
      this.entry = entry;
      this.name = name;
    }

    @Override
    public T get() {
      return entry.<ScalarMart<T>>getMart().get();
    }

    @Override
    public void set(T value) {
      if (created) {
        entry.<ScalarMart<T>>getMart().set(value);
      } else {
        put(name, new ScalarMart<>(value));
        entry = indices.get(name);
      }
      created = true;
    }
  }
}
