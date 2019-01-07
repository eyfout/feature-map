package ht.eyfout.map.element;

public interface Scalar<T> extends FeatureSupporter, Element {
  void set(T value);
  T get();
}
