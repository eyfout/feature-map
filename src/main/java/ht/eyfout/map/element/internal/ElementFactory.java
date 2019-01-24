package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import ht.eyfout.map.scalar.ScalarReference;

public final class ElementFactory {
  public static GroupElement create(
      GroupDataStorage dataProvider, FeatureBundle bundle, RuntimeContext context) {
    return new GroupElement(dataProvider, bundle, context);
  }

  public static <T> Scalar<T> create(Group element, String name) {
    GroupElement groupElement = (GroupElement) element;
    return ScalarReference.getScalar(name, element);
  }
}
