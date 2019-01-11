package ht.eyfout.map.factory;

import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.ElementFactory;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import javax.inject.Inject;

// FIXME import should NOT have internal, create interface
public class FeatureElementMapFactory {

  @Inject
  protected FeatureElementMapFactory() {}

  public Group group(GroupDataMart store, FeatureBundle bundle, RuntimeContext context) {
    return ElementFactory.create(store, bundle, context);
  }

  public <T> Scalar<T> scalar(Group element, ScalarMart<T> scalarStore, String name) {
    return ElementFactory.create(element, scalarStore, name);
  }
}
