package ht.eyfout.map.data.storage;

public final class ScalarMart<T> implements DataMart {
  T scalarValue;

  public ScalarMart() {}

  public ScalarMart(T initialValue) {
    scalarValue = initialValue;
  }

  public void set(T value) {
    scalarValue = value;
  }

  public T get() {
    return scalarValue;
  }

  @Override
  public int size() {
    return 1;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <D extends DataMart> D copy() {
    return (D) new ScalarMart<>(scalarValue);
  }
}
