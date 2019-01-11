package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.ScalarMart;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataMart;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public class GroupElement extends AbstractFeatureBundleFeatureSupporter implements Group {
  protected RuntimeContext context;
  private GroupDataMart dataStore;

  protected GroupElement(GroupDataMart dataStore, FeatureBundle bundle, RuntimeContext context) {
    super(bundle);
    this.dataStore =
        dataStore.isImmutable() ? DeltaStoreGroupDataMart.create(dataStore) : dataStore;
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
      // TODO
    }
  }

  @Override
  public <T> T getScalarValue(String name) {
    ScalarMart<T> scalarProvider = dataStore.<ScalarMart<T>>get(name);
    final T value = (null == scalarProvider) ? null : scalarProvider.get();
    return chain()
        .map((pgFeature) -> ((GroupFeature) pgFeature).getScalarValue(name, value, this, context))
        .orElse(value);
  }

  @Override
  public <T> Scalar<T> getScalar(String name) {
    final ScalarElement<T> scalar =
        ElementFactory.create(dataStore.<ScalarMart<T>>get(name), bundle(), context);
    return chain()
        .map((pgFeature) -> ((GroupFeature) pgFeature).getScalar(name, scalar, this, context))
        .orElse(scalar);
  }

  @Override
  public <T extends FeatureOperation> T operation(FeatureDescriptor feature) {
    return definition(feature).operations(context);
  }
}
