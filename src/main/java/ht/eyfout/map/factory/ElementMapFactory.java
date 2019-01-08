package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
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
  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar) {
    this.registrar = registrar;
  }

  public Group group(GroupDataStore pgDataProvider, Feature... feature) {
    return ElementFactory.create(pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(Feature...feature){
    return group(new MapGroupDataStore(), feature);
  }

  public <T> Scalar<T> scalar(Group element, ScalarStore<T> scalarStore) {
    return ElementFactory.create(element, scalarStore, null);
  }

  public <T> Scalar<T> scalar(Group element){
    return scalar(element, new ScalarStore<T>());
  }
}
