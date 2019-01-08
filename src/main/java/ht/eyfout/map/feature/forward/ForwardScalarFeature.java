package ht.eyfout.map.feature.forward;

import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.ScalarFeature;

public final class ForwardScalarFeature extends ScalarFeature {
  FeatureProfile profile;

  public ForwardScalarFeature(ScalarFeature feature, FeatureProfile profile) {
    super(feature);
    this.profile = profile;
  }

  @Override
  public FeatureProfile profile() {
    return profile;
  }
}
