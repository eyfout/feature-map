package ht.eyfout.map.data.storage.array;

import java.util.Map;
import java.util.Optional;

public class Keys {
  Map<String, Integer> indices;

  public Keys(Map<String, Integer> indices) {
    this.indices = indices;
  }

  public int index(String name) {
    return indices.get(name);
  }

  public Optional<String> name(int index) {
    for (Map.Entry<String, Integer> entry : indices.entrySet()) {
      if (entry.getValue() == index) {
        return Optional.of(entry.getKey());
      }
    }
    return Optional.empty();
  }
}
