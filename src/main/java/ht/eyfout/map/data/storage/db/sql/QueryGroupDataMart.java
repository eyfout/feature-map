package ht.eyfout.map.data.storage.db.sql;

import ht.eyfout.map.data.storage.map.MapGroupDataMart;

public class QueryGroupDataMart extends MapGroupDataMart {

  @Override
  public boolean isImmutable() {
    return true;
  }

  public static interface QueryGroupDataStoreBuilder extends DataStoreBuilder<QueryGroupDataMart> {

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
