package ht.eyfout.map.element.internal;

import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.UnsupportedFeatureException;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class AbstractFeatureContainer<T> extends AbstractFeatureBundleFeatureSupporter
    implements FeatureSupporter.Container<T> {

  Map<T, FeatureBundle> scalarBundles;
  Function<FeatureFactory, FeatureDefinition> func;

  public AbstractFeatureContainer(FeatureBundle bundle) {
    super(bundle);
    func = FeatureFactory.elementFunctions(Scalar.class);
    scalarBundles = new HashMap<>();
  }

  @Override
  public void addFeature(T ref, Feature feature) {
    scalarBundles.put(ref, getFeatureBundle(ref).withFeature(feature));
  }

  @Override
  public void removeFeature(T ref, Feature feature) {
    scalarBundles.put(ref, getFeatureBundle(ref).withoutFeature(feature));
  }

  @Override
  public boolean hasFeature(T ref, Feature feature) {
    boolean result = false;
    try {
      getFeatureBundle(ref).get(feature, func);
      result = true;
    } catch (UnsupportedFeatureException e) {
      e.printStackTrace();
    }
    return result;
  }

  //  @Override
  public FeatureDefinition definition(T ref, FeatureDescriptor descriptor) {
    return getFeatureBundle(ref).get(descriptor, func);
  }

  private FeatureBundle getFeatureBundle(T ref) {
    FeatureBundle bundle = scalarBundles.get(ref);
    if (null == bundle) {
      bundle = bundle();
      scalarBundles.put(ref, bundle);
    }
    return bundle;
  }
}
