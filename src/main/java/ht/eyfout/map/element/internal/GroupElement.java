package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public class GroupElement extends AbstractFeatureBundleFeatureSupporter implements Group {
  private GroupDataStore dataStore;
  protected RuntimeContext context;

  protected GroupElement(GroupDataStore dataStore, FeatureBundle bundle, RuntimeContext context) {
    super(bundle);
    this.dataStore =
        dataStore.isImmutable() ? DeltaStoreGroupDataStore.create(dataStore) : dataStore;
    this.context = context;
  }

  @Override
  public <T> void putScalarValue(String name, T value) {
    try {
      dataStore.put(
          name,
          dataStore.createScalarProvider(
              chain()
                  .map(
                      (pgFeature) ->
                          ((GroupFeature) pgFeature).putScalarValue(name, value, this, context))
                  .orElse(value)));
    } catch (StoppedFeatureChainException e) {
      //TODO
    }
  }

  @Override
  public <T> T getScalarValue(String name) {
    ScalarStore<T> scalarProvider = dataStore.<ScalarStore<T>>get(name);
    final T value = (null == scalarProvider) ? null : scalarProvider.get();
    return chain()
        .map((pgFeature) -> ((GroupFeature) pgFeature).getScalarValue(name, value, this, context))
        .orElse(value);
  }

  @Override
  public <T> Scalar<T> getScalar(String name) {
    final ScalarElement<T> scalar =
        ElementFactory.create(dataStore.<ScalarStore<T>>get(name), bundle(), context);
    return chain()
        .map((pgFeature) -> ((GroupFeature) pgFeature).getScalar(name, scalar, this, context))
        .orElse(scalar);
  }

  @Override
  public <T extends FeatureOperation> T operations(FeatureDescriptor feature) {
    return definition(feature).runtimeData(context);
  }
}
