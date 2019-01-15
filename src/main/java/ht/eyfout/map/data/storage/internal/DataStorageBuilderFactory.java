package ht.eyfout.map.data.storage.internal;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import ht.eyfout.map.data.storage.GroupDataStorage;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public class DataStorageBuilderFactory implements
    ht.eyfout.map.data.storage.DataStorageBuilderFactory {

  private final Map<Class, Provider<DataStorageBuilder>> builders;

  @Inject
  protected DataStorageBuilderFactory(Map<Class, Provider<DataStorageBuilder>> builders) {
    this.builders = builders;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends GroupDataStorage, B extends DataStorageBuilder<T>> B create(Class<T> clazz) {
    return (B) builders.get(clazz).get();
  }
}
