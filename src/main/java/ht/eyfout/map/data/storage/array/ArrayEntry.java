package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataStorage;

public final class ArrayEntry {
  private final ArrayGroupDataStorage mart;
  private final int index;
  ArrayEntry(int index, ArrayGroupDataStorage mart){
    this.index = index;
    this.mart = mart;
  }

  int index(){
    return index;
  }

  @SuppressWarnings("unchecked")
  public <T extends DataStorage> T getMart() {
    return (T)mart.getDataMart()[index];
  }

  public ArrayGroupDataStorage source(){
    return mart;
  }
}
