package ht.eyfout.map.data.storage.internal;

import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import ht.eyfout.map.data.storage.GroupDataStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;

public class DataStoreFactory implements ht.eyfout.map.data.storage.DataStoreFactory {

  Map<Class, DataStoreBuilder> builders = new HashMap<>();

  @Inject
  protected DataStoreFactory(Set<DataStoreBuilder> builders) {
    final String methodName = DataStoreBuilder.class.getDeclaredMethods()[0].getName();
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
  public <T extends GroupDataStore, B extends DataStoreBuilder<T>> B create(Class<T> clazz) {
    return (B) builders.get(clazz);
  }
}
