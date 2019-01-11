package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public class ScalarElement<T> extends AbstractFeatureBundleFeatureSupporter implements Scalar<T> {
  private ScalarMart<T> dataStore;
  private RuntimeContext context;

  public ScalarElement(ScalarMart<T> dataStore, FeatureBundle bundle, RuntimeContext context) {
    super(bundle);
    this.context = context;
    this.dataStore = dataStore;
  }

  @Override
  public void set(T value) {
    dataStore.set(value);
  }

  @Override
  public T get() {
    return dataStore.get();
  }
}
