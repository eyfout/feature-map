package ht.eyfout.map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ht.eyfout.map.data.storage.DataStore;
import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import ht.eyfout.map.data.storage.DataStoreFactory;
import ht.eyfout.map.data.storage.map.MapDataStoreBuilder;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.deltastore.DeltaStoreRuntime;
import ht.guice.ElementGuiceModule;
import java.util.Map;

public class Main {
  public static void main(String[] args) throws NoSuchMethodException {
    final Injector injector = Guice.createInjector(new ElementGuiceModule());
    DataStoreFactory factory = injector.getInstance(DataStoreFactory.class);
    MapDataStoreBuilder builder = (MapDataStoreBuilder)factory.create(MapGroupDataStore.class);
    builder.build();

    ElementMapFactory elementMapFactory = injector.getInstance(ElementMapFactory.class);
    Group groupElement = elementMapFactory.group();
    groupElement.addFeature(Feature.DELTA_STORE);

    String key = "John";
    groupElement.putScalarValue(key, 43);
    DeltaStoreRuntime dlta = groupElement.<DeltaStoreRuntime>runtimeData(Feature.DELTA_STORE);
    groupElement.putScalarValue(key + "001", 57);
    groupElement.putScalarValue(key + "002", 73);

    Scalar<Integer> scalar = groupElement.<Integer>getScalar(key);


    System.out.println( groupElement.<Integer>getScalar(key).get() );
    System.out.println( "Size: " + dlta.size());
  }


  static void printRank(FeatureDescriptor feature) {
    System.out.println(String.format("%s : %d", feature.name()));
  }
}
