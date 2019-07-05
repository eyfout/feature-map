package ht.eyfout.map;

import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.data.storage.db.sql.QueryGroupDataStorage;
import ht.eyfout.map.data.storage.db.sql.QueryGroupDataStorage.QueryGroupDataStorageBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.visitor.ElementVisitor;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.deltastore.DeltaStoreOperations;
import ht.eyfout.map.visitor.VisitorResult;
import ht.guice.GuiceInstance;

public class Main {
  public static void main(String[] args) throws NoSuchMethodException {
    DataStorageBuilderFactory factory = GuiceInstance.get(DataStorageBuilderFactory.class);

    QueryGroupDataStorageBuilder queryBuilder = factory.create(QueryGroupDataStorageBuilder.class);
    QueryGroupDataStorage queryStore =
        queryBuilder
            .select()
            .fields("column1", "column2", "column3")
            .from("schema.tableName")
            .build();

    System.out.println(queryStore.<ScalarDataStorage<String>>get("SQL").get());

    ElementMapFactory elementMapFactory = GuiceInstance.get(ElementMapFactory.class);
    Group groupElement = elementMapFactory.group();

    String key = "John";
    groupElement.putScalarValue(key, 43);
    groupElement.addFeature(Feature.DELTA_STORE);

    DeltaStoreOperations delta = groupElement.<DeltaStoreOperations>operations(Feature.DELTA_STORE);
    groupElement.putScalarValue(key + "001", 57);
    groupElement.putScalarValue(key + "002", 73);

    System.out.println(groupElement.<Integer>getScalar(key).get());
    System.out.println("Size: " + delta.size());

    System.out.println( groupElement.accept(visitor()) );
  }

  static void printRank(FeatureDescriptor feature) {
    System.out.println(String.format("%s : %d", feature.name()));
  }

  public static ElementVisitor<String> visitor(){
    return new ElementVisitor<String>(){
      StringBuffer buffer = new StringBuffer(30);

      @Override
      public void pre(Group element) {
        buffer.append("{\n");
      }

      @Override
      public String post(Group element) {
        return buffer.append("}\n").toString();
      }

      @Override
      public VisitorResult visit(String name, Scalar element) {
        buffer.append(name).append(":").append(element.get()).append("\n");
        return VisitorResult.CONTINUE;
      }
    };
  }
}
