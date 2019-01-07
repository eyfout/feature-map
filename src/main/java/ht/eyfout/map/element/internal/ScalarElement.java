package ht.eyfout.map.element.internal;

import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.registrar.FeatureRegistrar.FeatureBundle;

public class ScalarElement extends AbstractFeatureBundleFeatureSupporter implements Scalar {
  public ScalarElement(
      FeatureBundle bundle) {
    super(bundle);
  }
}
