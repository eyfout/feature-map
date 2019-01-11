package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.data.storage.map.MapGroupDataMart;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.ElementFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import javax.inject.Inject;

public class ElementMapFactory {
  final FeatureRegistrar registrar;
  final DataMartFactory dsFactory;

  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar, DataMartFactory dsFactory) {
    this.registrar = registrar;
    this.dsFactory = dsFactory;
  }

  public Group group(GroupDataMart pgDataProvider, Feature... feature) {
    return ElementFactory.create(
        pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(Feature... feature) {
    return group(dsFactory.create(MapGroupDataMart.class).build(), feature);
  }

  public <T> Scalar<T> scalar(Group element, ScalarMart<T> scalarStore) {
    return ElementFactory.create(element, scalarStore, null);
  }

  public <T> Scalar<T> scalar(Group element) {
    return scalar(element, new ScalarMart<T>());
  }
}
