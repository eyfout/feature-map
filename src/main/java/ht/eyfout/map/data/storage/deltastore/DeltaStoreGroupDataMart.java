package ht.eyfout.map.data.storage.deltastore;

import ht.eyfout.map.data.storage.DataMart;
import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.map.MapGroupDataMart;

public class DeltaStoreGroupDataMart extends MapGroupDataMart {

  private GroupDataMart baseProvider;

  private DeltaStoreGroupDataMart(GroupDataMart baseProvider) {
    this.baseProvider = baseProvider;
  }

  public static GroupDataMart create(GroupDataMart baseProvider) {
    return new DeltaStoreGroupDataMart(baseProvider);
  }

  @Override
  public <T extends DataMart> T get(String name) {
    T result = super.get(name);
    if (null == result) {
      result = baseProvider.get(name);
    }
    return result;
  }

  public static class DeltaStoreGroupDataStoreBuilder
      implements DataStoreBuilder<DeltaStoreGroupDataMart> {

    public DeltaStoreGroupDataMart group(GroupDataMart group) {
      return new DeltaStoreGroupDataMart(group);
    }

    @Override
    public DeltaStoreGroupDataMart build() {
      return null;
    }
  }
}
