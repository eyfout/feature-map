package ht.eyfout.map.data.storage.internal;

import ht.eyfout.map.data.storage.DataMart.DataMartBuilder;
import ht.eyfout.map.data.storage.GroupDataMart;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

public class DataMartFactory implements ht.eyfout.map.data.storage.DataMartFactory {

  Map<Class, DataMartBuilder> builders = new HashMap<>();

  @Inject
  protected DataMartFactory(Set<DataMartBuilder> builders) {
    final String methodName = DataMartBuilder.class.getDeclaredMethods()[0].getName();
    builders
        .stream()
        .forEach(
            (builder) -> {
              try {
                this.builders.put(
                    builder.getClass().getDeclaredMethod(methodName).getReturnType(), builder);
              } catch (NoSuchMethodException e) {
                e.printStackTrace();
              }
            });
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends GroupDataMart, B extends DataMartBuilder<T>> B create(Class<T> clazz) {
    return (B) builders.get(clazz);
  }
}
