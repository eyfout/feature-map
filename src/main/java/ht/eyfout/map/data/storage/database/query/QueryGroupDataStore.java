package ht.eyfout.map.data.storage.database.query;

import ht.eyfout.map.data.storage.map.MapGroupDataStore;

public class QueryGroupDataStore extends MapGroupDataStore {

  @Override
  public boolean isImmutable() {
    return true;
  }

  public static interface QueryGroupDataStoreBuilder extends DataStoreBuilder<QueryGroupDataStore> {

    Field select();

    interface Field extends Everything<DataSource> {
      DataSource fields(String... name);
    }

    interface Everything<R> {
      R all();
    }

    interface DataSource {
      QueryGroupDataStoreBuilder from(String table);
    }
  }
}
