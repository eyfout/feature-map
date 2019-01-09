package ht.eyfout.map.data.storage.map;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import javax.inject.Inject;

public class MapDataStoreBuilder implements DataStoreBuilder <MapGroupDataStore> {

  @Inject
  protected MapDataStoreBuilder() {
    //
  }

  @Override
  public MapGroupDataStore build() {
    return new MapGroupDataStore();
  }
}
