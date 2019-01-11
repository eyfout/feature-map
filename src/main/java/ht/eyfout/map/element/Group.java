package ht.eyfout.map.element;

public interface Group extends FeatureSupporter, Element {
  <T> void putScalarValue(String name, T value);

  <T> T getScalarValue(String name);

  <T> Scalar<T> getScalar(String name);
}
