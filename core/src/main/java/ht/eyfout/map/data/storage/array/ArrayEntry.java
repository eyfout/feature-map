package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;

public final class ArrayEntry {
  private final ArrayGroupDataStorage storage;
  private final int index;
  ArrayEntry(int index, ArrayGroupDataStorage storage){
    this.index = index;
    this.storage = storage;
  }

  int index(){
    return index;
  }

  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T getstorage() {
    return (T)storage.getDatastorage()[index];
  }

  public ArrayGroupDataStorage source(){
    return storage;
  }
}
