package ht.eyfout.map.data.storage;

public class ScalarStorage<T> implements DataStorage {
  T scalarValue;

  public ScalarStorage() {}

  public ScalarStorage(T initialValue) {
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
  public <D extends DataStorage> D copy() {
    return (D) new ScalarStorage<>(scalarValue);
  }
}
