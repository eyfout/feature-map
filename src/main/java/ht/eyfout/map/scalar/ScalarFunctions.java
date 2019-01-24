package ht.eyfout.map.scalar;

import ht.eyfout.map.element.FeatureSupporter.Container;
import ht.eyfout.map.element.Group;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ScalarFunctions {
  static <R, T> Supplier<R> get(T name, Container container) {
    return () -> ((Group) container).getScalarValue((String) name);
  }

  static <R, T> Consumer<R> set(T name, Container container) {
    return (value) -> ((Group) container).putScalarValue((String) name, value);
  }
}
