package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.DataStoreFactory;
import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.data.storage.map.MapDataStoreBuilder;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.ElementFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import javax.inject.Inject;

public class ElementMapFactory {
  final FeatureRegistrar registrar;
  final DataStoreFactory dsFactory;
  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar, DataStoreFactory dsFactory) {
    this.registrar = registrar;
    this.dsFactory = dsFactory;
  }

  public Group group(GroupDataStore pgDataProvider, Feature... feature) {
    return ElementFactory.create(pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(Feature...feature){
    MapDataStoreBuilder builder = (MapDataStoreBuilder)dsFactory.create(MapGroupDataStore.class);
    return group( builder.build(), feature);
  }

  public <T> Scalar<T> scalar(Group element, ScalarStore<T> scalarStore) {
    return ElementFactory.create(element, scalarStore, null);
  }

  public <T> Scalar<T> scalar(Group element){
    return scalar(element, new ScalarStore<T>());
  }
}
