package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage.MapGroupDataStorageBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.ElementFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import javax.inject.Inject;

public class ElementMapFactory {
  final FeatureRegistrar registrar;
  final DataStorageBuilderFactory dsFactory;

  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar, DataStorageBuilderFactory dsFactory) {
    this.registrar = registrar;
    this.dsFactory = dsFactory;
  }

  public Group group(GroupDataStorage pgDataProvider, Feature... feature) {
    return ElementFactory.create(
        pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(DataStorageBuilder<? extends GroupDataStorage> builder, Feature... features) {
    return group(builder.build(), features);
  }

  public Group group(Feature... feature) {
    return group(
        dsFactory.<MapGroupDataStorage, MapGroupDataStorageBuilder>create(MapGroupDataStorage.class),
        feature);
  }

  public <T> Scalar<T> scalar(Group element, ScalarStorage<T> scalarStore) {
    return ElementFactory.create(element, scalarStore, null);
  }

  public <T> Scalar<T> scalar(Group element) {
    return scalar(element, new ScalarStorage<T>());
  }
}
