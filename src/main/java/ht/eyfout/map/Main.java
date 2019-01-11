package ht.eyfout.map;

import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.data.storage.db.sql.QueryGroupDataMart;
import ht.eyfout.map.data.storage.db.sql.QueryGroupDataMart.QueryGroupDataStoreBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.deltastore.DeltaStoreOperations;
import ht.guice.GuiceInstance;

public class Main {
  public static void main(String[] args) throws NoSuchMethodException {
    DataMartFactory factory = GuiceInstance.get(DataMartFactory.class);

    QueryGroupDataStoreBuilder queryBuilder = factory.create(QueryGroupDataMart.class);
    QueryGroupDataMart queryStore =
        queryBuilder
            .select()
            .fields("column1", "column2", "column3")
            .from("schema.tableName")
            .build();

    System.out.println(queryStore.<ScalarMart<String>>get("SQL").get());

    ElementMapFactory elementMapFactory = GuiceInstance.get(ElementMapFactory.class);
    Group groupElement = elementMapFactory.group();
    groupElement.addFeature(Feature.DELTA_STORE);

    String key = "John";
    groupElement.putScalarValue(key, 43);
    DeltaStoreOperations delta = groupElement.<DeltaStoreOperations>operations(Feature.DELTA_STORE);
    groupElement.putScalarValue(key + "001", 57);
    groupElement.putScalarValue(key + "002", 73);

    Scalar<Integer> scalar = groupElement.<Integer>getScalar(key);

    System.out.println(groupElement.<Integer>getScalar(key).get());
    System.out.println("Size: " + delta.size());
  }

  static void printRank(FeatureDescriptor feature) {
    System.out.println(String.format("%s : %d", feature.name()));
  }
}
