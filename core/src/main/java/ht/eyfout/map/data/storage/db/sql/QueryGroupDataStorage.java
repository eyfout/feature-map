package ht.eyfout.map.data.storage.db.sql;

import ht.eyfout.map.data.storage.map.MapGroupDataStorage;

public class QueryGroupDataStorage extends MapGroupDataStorage {

  @Override
  public boolean isImmutable() {
    return true;
  }

  public interface QueryGroupDataStorageBuilder extends
      DataStorageBuilder<QueryGroupDataStorage> {

    Field select();

    interface Field extends Everything<DataSource> {
      DataSource fields(String... name);
    }

    interface Everything<R> {
      R all();
    }

    interface DataSource {
      QueryGroupDataStorageBuilder from(String table);
    }
  }
}
