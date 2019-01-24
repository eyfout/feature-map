package ht.eyfout.map.data.storage.array;

public final class ArrayEntry {
  private final ArrayGroupDataStorage storage;
  private final int index;

  ArrayEntry(int index, ArrayGroupDataStorage storage) {
    this.index = index;
    this.storage = storage;
  }

  int index() {
    return index;
  }

  @SuppressWarnings("unchecked")
  public <T> T value() {
    return (T) source().getDataStorage()[index];
  }

  public ArrayGroupDataStorage source() {
    return storage;
  }
}
