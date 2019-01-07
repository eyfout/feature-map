package ht.eyfout.map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.element.Group;
import ht.guice.ElementGuiceModule;

public class Main {
  public static void main(String[] args) {
    final Injector injector = Guice.createInjector(new ElementGuiceModule());

    ElementMapFactory elementMapFactory = injector.getInstance(ElementMapFactory.class);
    Group group = elementMapFactory.group();
    group.addFeature(Feature.DELTA_STORE);

    String key = "John";
    group.putScalarValue(key, 43);
    System.out.println( key + ":" + group.<Integer>getScalarValue(key) );
  }

  static void printRank(FeatureDescriptor feature) {
    System.out.println(String.format("%s : %d", feature.name()));
  }
}
