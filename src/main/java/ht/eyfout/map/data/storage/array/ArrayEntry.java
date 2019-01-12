package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;

public final class ArrayEntry {
  private final ArrayGroupDataMart mart;
  private final int index;
  ArrayEntry(int index, ArrayGroupDataMart mart){
    this.index = index;
    this.mart = mart;
  }

  int index(){
    return index;
  }

  @SuppressWarnings("unchecked")
  public <T extends DataMart> T getMart() {
    return (T)mart.getDataMart()[index];
  }
}
