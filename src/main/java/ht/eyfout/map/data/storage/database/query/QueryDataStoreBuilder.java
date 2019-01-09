package ht.eyfout.map.data.storage.database.query;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;

public interface QueryDataStoreBuilder extends DataStoreBuilder<QueryGroupDataStore> {

  Field select();

  interface Field extends Everything<DataSource> {
    DataSource fields(String... name);
  }

  interface Everything<R> {
    R all();
  }

  interface DataSource {
    QueryDataStoreBuilder from(String table);
  }
}
