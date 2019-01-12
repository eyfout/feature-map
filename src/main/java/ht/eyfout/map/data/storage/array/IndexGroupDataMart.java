package ht.eyfout.map.data.storage.array;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;

public class IndexGroupDataMart implements GroupDataMart {

  private ArrayGroupDataMart arrayStore;

  public IndexGroupDataMart(ArrayGroupDataMart store) {
    this.arrayStore = store;
  }

  @Override
  public <T extends DataMart> void put(String name, T provider) {
    arrayStore.put(name, provider);
  }

  @Override
  public <T extends DataMart> T get(String name) {
    return arrayStore.get(name);
  }

  @Override
  public int size() {
    return arrayStore.size();
  }

  public <T extends DataMart> T get(int index) {
    return (T) arrayStore.getByIndex(index);
  }

  public <T extends DataMart> void put(int index, T store) {
    arrayStore.getDataMart()[index] = store;
  }

  public Keys keys() {
    return new Keys(arrayStore.getNameFunc(), arrayStore.getIndexFunc());
  }

  public static class IndexGroupDataMartBuilder implements DataMartBuilder<IndexGroupDataMart> {

    public IndexGroupDataMartBuilder() {}

    public IndexGroupDataMart array(ArrayGroupDataMart store) {
      return new IndexGroupDataMart(store);
    }

    @Override
    public IndexGroupDataMart build() {
      throw new IllegalStateException();
    }
  }
}
