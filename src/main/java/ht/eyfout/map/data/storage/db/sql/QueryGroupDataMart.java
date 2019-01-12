package ht.eyfout.map.data.storage.db.sql;

import ht.eyfout.map.data.storage.map.MapGroupDataMart;

public class QueryGroupDataMart extends MapGroupDataMart {

  @Override
  public boolean isImmutable() {
    return true;
  }

  public static interface QueryGroupDataMartBuilder extends DataMartBuilder<QueryGroupDataMart> {

    Field select();

    interface Field extends Everything<DataSource> {
      DataSource fields(String... name);
    }

    interface Everything<R> {
      R all();
    }

    interface DataSource {
      QueryGroupDataMartBuilder from(String table);
    }
  }
}
