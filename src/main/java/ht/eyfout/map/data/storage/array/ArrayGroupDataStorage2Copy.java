package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;
import java.util.HashMap;

public class ArrayGroupDataStorage2Copy extends ArrayGroupDataStorage2 {

  private boolean hasLocalChanges;

  ArrayGroupDataStorage2Copy(ArrayGroupDataStorage2 arr) {
    indices = arr.indices;
    indicesInt = arr.indicesInt;
  }

  @Override
  public <T extends DataStorage> void putAsDataStore(String name, T dataStore) {
    modifying(name);
    super.putAsDataStore(name, dataStore);
  }

  @Override
  public <T> void put(String name, T value) {
    modifying(name);
    super.put(name, value);
  }

  @Override
  public <T extends DataStorage> T getAsDataStore(String name) {
    return get(name);
  }

  @Override
  public <T> T get(String name) {
    ArrayEntry entry = indices.get(name);
    if(null == entry){
      return null;
    }
    return entry.value();
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
}
