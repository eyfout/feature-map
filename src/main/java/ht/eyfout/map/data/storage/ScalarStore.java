package ht.eyfout.map.data.storage;

public final class ScalarStore<T> implements DataStore {
  T scalarValue;

  public ScalarStore() {}

  public ScalarStore(T initialValue) {
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
}
