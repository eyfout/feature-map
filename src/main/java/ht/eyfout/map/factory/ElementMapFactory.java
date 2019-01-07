package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.internal.ElementFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import javax.inject.Inject;

public final class ElementMapFactory {
  final FeatureRegistrar registrar;
  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar) {
    this.registrar = registrar;
  }

  public Group group(GroupDataStore pgDataProvider, Feature... feature) {
    return ElementFactory.create(pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(Feature...feature){
    return group(new ArrayGroupDataStore(), feature);
  }
}
