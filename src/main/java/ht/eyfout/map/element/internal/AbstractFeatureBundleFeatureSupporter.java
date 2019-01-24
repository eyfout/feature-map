package ht.eyfout.map.element.internal;

import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.UnsupportedFeatureException;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractFeatureBundleFeatureSupporter implements FeatureSupporter {
  private FeatureBundle bundle;
  private Function<FeatureFactory, FeatureDefinition> func;

  public AbstractFeatureBundleFeatureSupporter(FeatureBundle bundle) {
    this.bundle = bundle;
    func = FeatureFactory.elementFunctions(this.getClass());
  }

  @Override
  public FeatureBundle bundle() {
    return bundle;
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
  public <T extends FeatureDefinition> Optional<T> chain() {
    return (Optional<T>) Optional.ofNullable(bundle.<FeatureDefinition>chain(func));
  }

  @Override
  public boolean hasFeature(FeatureDescriptor feature) {
    boolean result = false;
    try {
      definition(feature);
      result = true;
    } catch (UnsupportedFeatureException ex) {
      ex.printStackTrace();
    }
    return result;
  }

  @Override
  public FeatureDefinition definition(FeatureDescriptor descriptor) {
    return bundle.get(descriptor, func);
  }
}
