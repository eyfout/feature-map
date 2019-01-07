package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public final class ElementFactory {
  public static GroupElement create(GroupDataStore dataProvider, FeatureBundle bundle, RuntimeContext context) {
    return new GroupElement(dataProvider, bundle, context);
  }

  public static <T> ScalarElement<T> create(ScalarStore<T> store, FeatureBundle bundle, RuntimeContext context) {
    return new ScalarElement<T>(store, bundle, context);
  }


}
