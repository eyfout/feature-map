package ht.eyfout.struct.data;

import java.util.Map;

public final class ElementKeyResolver {
  private Integer[] index;
  private final Map<String, Integer> nameToInt;

  public ElementKeyResolver(Map<String, Integer> nameToInt) {
    this.nameToInt = nameToInt;
    Integer n = 0;
    for (Integer value : nameToInt.values()) {
      if (value > n) {
        n = value;
      }
    }
    n+=5;

    index = new Integer[n];
    for (n = 0; n < index.length; n++) {
      index[n] = n;
    }
  }

  public int getIndexFor(int propId) {
    return index[propId];
  }

  public Integer getIndexFor(final String name) {
    Integer result = nameToInt.computeIfAbsent(name, it->nameToInt.values().size());
    index = Elements.expandArray(index, result);
    index[result] = result;
    return  result;
  }


}
