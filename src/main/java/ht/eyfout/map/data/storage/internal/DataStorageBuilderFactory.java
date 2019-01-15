package ht.eyfout.map.data.storage.internal;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import ht.eyfout.map.data.storage.GroupDataStorage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

public class DataStorageBuilderFactory implements
    ht.eyfout.map.data.storage.DataStorageBuilderFactory {

  Map<Class, DataStorageBuilder> builders = new HashMap<>();

  @Inject
  protected DataStorageBuilderFactory(Set<DataStorageBuilder> builders) {
    final String methodName = DataStorageBuilder.class.getDeclaredMethods()[0].getName();
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
  public <T extends GroupDataStorage, B extends DataStorageBuilder<T>> B create(Class<T> clazz) {
    return (B) builders.get(clazz);
  }
}
