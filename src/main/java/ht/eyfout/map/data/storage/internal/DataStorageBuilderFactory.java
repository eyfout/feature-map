package ht.eyfout.map.data.storage.internal;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public class DataStorageBuilderFactory
    implements ht.eyfout.map.data.storage.DataStorageBuilderFactory {

  private final Map<Class, Provider<DataStorageBuilder>> builders;

  @Inject
  protected DataStorageBuilderFactory(Map<Class, Provider<DataStorageBuilder>> builders) {
    this.builders = builders;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <B extends DataStorageBuilder> B create(Class<B> clazz) {
    return (B) builders.get(clazz).get();
  }
}
