package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import java.util.HashMap;

public class ArrayGroupDataMart2Copy extends ArrayGroupDataMart2 {

  int sizeOfOrigin;

  ArrayGroupDataMart2Copy(ArrayGroupDataMart2 arr) {
    sizeOfOrigin = Integer.MIN_VALUE;
    indices = arr.indices;
    indicesInt = arr.indicesInt;
  }

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    modifying();
    super.put(name, provider);
  }

  @Override
  public <T extends DataMart> T get(String name) {
    T result = super.get(name);
    if(sizeOfOrigin > getIndexFunc().apply(name)){
      result = (T)result.copy();
      put(name, result);
    }
    return result;
  }

  private void modifying() {
    if (0 > sizeOfOrigin) {
      sizeOfOrigin = indices.size();
      indices = new HashMap<>(indices);
      indicesInt = new HashMap<>(indicesInt);
      nextIndex = indices.size() + 1;
    }
  }
}
