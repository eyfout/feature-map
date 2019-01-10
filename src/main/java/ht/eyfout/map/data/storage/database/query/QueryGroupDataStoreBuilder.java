package ht.eyfout.map.data.storage.database.query;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public interface QueryGroupDataStoreBuilder extends DataStoreBuilder<QueryGroupDataStore> {

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
