package ht.eyfout.map.element.internal;

import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.features.GroupFeature;
import ht.eyfout.map.registrar.FeatureRegistrar.FeatureBundle;
import java.util.Optional;

public abstract class AbstractFeatureBundleFeatureSupporter implements FeatureSupporter {
  private FeatureBundle bundle;

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
  public Optional<GroupFeature> pageFeature() {
    return Optional.ofNullable(bundle.chain(FeatureFactory::pageFeature));
  }

  @Override
  public GroupFeature pageFeature(FeatureDescriptor descriptor) {
    return bundle.get(descriptor, FeatureFactory::pageFeature);
  }
}
