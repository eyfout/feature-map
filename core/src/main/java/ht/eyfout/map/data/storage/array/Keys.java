package ht.eyfout.map.data.storage.array;

import java.util.Optional;
import java.util.function.Function;

public class Keys {

  private final Function<String, Integer> indexFunc;
  private final Function<Integer, String> nameFunc;

  Keys(Function<Integer, String> nameFunc, Function<String, Integer> indexFunc) {
    this.nameFunc = nameFunc;
    this.indexFunc = indexFunc;
  }

  public int index(String name) {
    return indexFunc.apply(name);
  }

  public Optional<String> name(int index) {
    return Optional.ofNullable(nameFunc.apply(index));
  }
}
