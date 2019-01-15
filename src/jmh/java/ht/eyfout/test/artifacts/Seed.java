package ht.eyfout.test.artifacts;

import ht.eyfout.map.element.Group;

public class Seed {

  private Seed() {}

  public static void seedGroupElement(Group element, int size, String prefix) {
    for (int n = 0; n < size; n++) {
      element.putScalarValue(prefix + "n", n);
    }
  }
}
