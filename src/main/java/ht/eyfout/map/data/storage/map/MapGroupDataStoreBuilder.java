package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import javax.inject.Inject;

public class MapGroupDataStoreBuilder implements DataStoreBuilder <MapGroupDataStore> {

  @Inject
  protected MapGroupDataStoreBuilder() {
    //
  }

  @Override
  public MapGroupDataStore build() {
    return new MapGroupDataStore();
  }
}
