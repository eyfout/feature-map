package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.internal.GroupElement;
import ht.eyfout.map.element.internal.RuntimeContext;
import ht.eyfout.map.registrar.FeatureRegistrar;
import javax.inject.Inject;

public final class ElementMapFactory {
  final FeatureRegistrar registrar;
  @Inject
  protected ElementMapFactory(FeatureRegistrar registrar) {
    this.registrar = registrar;
  }

  public Group group(GroupDataStore pgDataProvider, Feature... feature) {
    return new GroupElement(pgDataProvider, registrar.bundle(feature), RuntimeContext.create());
  }

  public Group group(Feature...feature){
    return group(new MapGroupDataStore(), feature);
  }
}
