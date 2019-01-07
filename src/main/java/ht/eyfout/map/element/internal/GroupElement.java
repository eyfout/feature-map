package ht.eyfout.map.element.internal;

import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.ScalarStore;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.StoppedFeatureChainException;
import ht.eyfout.map.registrar.FeatureRegistrar.FeatureBundle;

public class GroupElement extends AbstractFeatureBundleFeatureSupporter implements Group {
  private GroupDataStore dataProvider;
  private RuntimeContext context;

  public GroupElement(GroupDataStore dataProvider, FeatureBundle bundle, RuntimeContext context) {
    super(bundle);
    this.dataProvider =
        dataProvider.isImmutable() ? DeltaStoreGroupDataStore.create(dataProvider) : dataProvider;
    this.context = context;
  }

  @Override
  public <T> void putScalarValue(String name, T value) {
    try {
      dataProvider.put(
          name,
          dataProvider.createScalarProvider(
              pageFeature()
                  .map((pgFeature) -> pgFeature.putScalarValue(name, value, context.get(name)))
                  .orElse(value)));
    } catch (StoppedFeatureChainException e) {
      System.out.println(e);
    }
  }

  @Override
  public <T> T getScalarValue(String name) {
    ScalarStore<T> scalarProvider = dataProvider.<ScalarStore<T>>get(name);
    final T value = (null == scalarProvider) ? null : scalarProvider.get();
    return pageFeature()
        .map((pgFeature) -> pgFeature.getScalarValue(name, value, context.get(name)))
        .orElse(value);
  }
}
