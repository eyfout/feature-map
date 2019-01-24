package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStorage;
import ht.eyfout.map.data.storage.visitor.internal.ElementDataStorageVisitor;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.visitor.ElementVisitor;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;

public class GroupElement extends AbstractFeatureContainer<String> implements Group {
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
          this.<GroupFeature>chain()
              .map((pgFeature) -> pgFeature.putScalarValue(name, value, this, context))
              .orElse(value));
    } catch (StoppedFeatureChainException e) {
      // TODO
    }
  }

  @Override
  public <T> T getScalarValue(String name) {
    final T value = dataStore.get(name);
    return this.<GroupFeature>chain()
        .map((pgFeature) -> pgFeature.getScalarValue(name, value, this, context))
        .orElse(value);
  }

  @Override
  public <T extends FeatureOperation> T operation(FeatureDescriptor feature) {
    return definition(feature).operations(context);
  }

  @Override
  public void putGroup(String name, Group group) {
    GroupElement groupElement = getAs(group);
    GroupDataStorage copy = groupElement.dataStore.copy();
    dataStore.putAsDataStore(
        name,
        this.<GroupFeature>chain()
            .map((pgFeature) -> pgFeature.putGroup(name, copy, this, context))
            .orElse(copy));
  }

  private GroupElement getAs(Group group) {
    if (group instanceof GroupElement) {
      return (GroupElement) group;
    }
    throw new UnsupportedOperationException();
  }

  @Override
  public <T> T accept(ElementVisitor visitor) {
    dataStore.accept(new ElementDataStorageVisitor(this, visitor));
    return visitor.result();
  }

  @Override
  public <O extends FeatureOperation> O operations(String ref, Feature feature) {
    return definition(ref, feature).operations(context.get(ref));
  }
}
