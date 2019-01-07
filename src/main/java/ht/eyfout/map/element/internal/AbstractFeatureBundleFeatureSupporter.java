package ht.eyfout.map.element.internal;

import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import java.util.Optional;

public abstract class AbstractFeatureBundleFeatureSupporter implements FeatureSupporter {
  private FeatureBundle bundle;

  @Override
  public FeatureBundle bundle() {
    return bundle;
  }

  public AbstractFeatureBundleFeatureSupporter(FeatureBundle bundle) {
    this.bundle = bundle;
  }

  @Override
  public void addFeature(FeatureDescriptor feature) {
    bundle = bundle.withFeature(feature);
  }

  @Override
  public void removeFeature(FeatureDescriptor feature) {
    bundle = bundle.withoutFeature(feature);
  }

  @Override
  public Optional<GroupFeature> groupFeature() {
    return Optional.ofNullable(bundle.chain(FeatureFactory::groupFeature));
  }

  @Override
  public GroupFeature groupFeature(FeatureDescriptor descriptor) {
    return bundle.get(descriptor, FeatureFactory::groupFeature);
  }
}
