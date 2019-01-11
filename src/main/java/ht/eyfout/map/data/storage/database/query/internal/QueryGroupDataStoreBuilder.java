package ht.eyfout.map.data.storage.database.query.internal;

import ht.eyfout.map.data.storage.database.query.QueryGroupDataStore;
import ht.sample.data.source.Database;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class QueryGroupDataStoreBuilder
    implements QueryGroupDataStore.QueryGroupDataStoreBuilder {

  StringBuffer buffer;
  Database db;

  @Inject
  public QueryGroupDataStoreBuilder(Database db) {
    buffer = new StringBuffer();
    this.db = db;
  }

  @Override
  public Field select() {
    buffer.append("SELECT");
    return new IField();
  }

  @Override
  public QueryGroupDataStore build() {
    QueryGroupDataStore store = new QueryGroupDataStore();
    store.put("SQL", store.createScalarProvider(buffer.toString()));
    return store;
  }

  private class IDataSource implements DataSource {

    IDataSource() {
      buffer.append(" ");
    }

    @Override
    public QueryGroupDataStore.QueryGroupDataStoreBuilder from(String table) {
      buffer.append("FROM ");
      buffer.append(table);
      return ht.eyfout.map.data.storage.database.query.internal.QueryGroupDataStoreBuilder.this;
    }
  }

  private class IField implements Field {

    IField() {
      buffer.append(" ");
    }

    @Override
    public DataSource fields(String... names) {
      if (0 == names.length) {
        return all();
      }

      List<String> columns = Arrays.asList(names);
      final Iterator<String> iterator = columns.iterator();
      buffer.append(iterator.next());
      while (iterator.hasNext()) {
        buffer.append(", ");
        buffer.append(iterator.next());
      }
      return new IDataSource();
    }

    @Override
    public DataSource all() {
      buffer.append("*");
      return new IDataSource();
    }
  }
}
