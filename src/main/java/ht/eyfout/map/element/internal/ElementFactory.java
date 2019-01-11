package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public final class ElementFactory {
  public static GroupElement create(
      GroupDataStore dataProvider, FeatureBundle bundle, RuntimeContext context) {
    return new GroupElement(dataProvider, bundle, context);
  }

  public static <T> ScalarElement<T> create(
      ScalarStore<T> store, FeatureBundle bundle, RuntimeContext context) {
    return new ScalarElement<T>(store, bundle, context);
  }

  public static <T> ScalarElement<T> create(Group element, ScalarStore<T> store, String name) {
    GroupElement groupElement = (GroupElement) element;
    // FIXME: If name is provided get context and bundle based on name
    FeatureBundle bundle = groupElement.bundle();
    RuntimeContext context = groupElement.context;
    return create(store, groupElement.bundle(), context);
  }
}
