package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStorage;
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
  private GroupDataStorage dataStore;

  protected GroupElement(GroupDataStorage dataStore, FeatureBundle bundle, RuntimeContext context) {
    super(bundle);
    this.dataStore =
        dataStore.isImmutable() ? DeltaStoreGroupDataStorage.create(dataStore) : dataStore;
    this.context = context;
  }

  @Override
  public <T> void putScalarValue(String name, T value) {
    try {
      dataStore.put(
          name,
          dataStore.createScalarProvider(
              this.<GroupFeature>chain()
                  .map(
                      (pgFeature) ->
                          pgFeature.putScalarValue(name, value, this, context))
                  .orElse(value)));
    } catch (StoppedFeatureChainException e) {
      // TODO
    }
  }

  @Override
  public <T> T getScalarValue(String name) {
    ScalarDataStorage<T> scalarProvider = dataStore.<ScalarDataStorage<T>>get(name);
    final T value = (null == scalarProvider) ? null : scalarProvider.get();
    return this.<GroupFeature>chain()
        .map((pgFeature) -> pgFeature.getScalarValue(name, value, this, context))
        .orElse(value);
  }

  @Override
  public <T> Scalar<T> getScalar(String name) {
    final ScalarDataStorage<T> scalar = dataStore.<ScalarDataStorage<T>>get(name);
    return ElementFactory.create(this, this.<GroupFeature>chain()
        .map((pgFeature) ->  pgFeature.getScalar(name, scalar, this, context))
        .orElse(scalar), name);
  }

  @Override
  public <T extends FeatureOperation> T operation(FeatureDescriptor feature) {
    return definition(feature).operations(context);
  }

  @Override
  public void putGroup(String name, Group group) {
    GroupElement groupElement = getAs(group);
    GroupDataStorage copy = groupElement.dataStore.copy();
    dataStore.put(name, this.<GroupFeature>chain()
        .map((pgFeature) -> pgFeature.putGroup(name, copy, this, context))
        .orElse(copy));
  }

  private GroupElement getAs(Group group){
    if( group instanceof GroupElement){
      return (GroupElement)group;
    }
    throw new UnsupportedOperationException();
  }
}
