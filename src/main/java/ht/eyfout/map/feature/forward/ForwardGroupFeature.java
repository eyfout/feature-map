package ht.eyfout.map.feature.forward;

import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;

public final class ForwardGroupFeature extends GroupFeature {
  private FeatureProfile profile;

  protected ForwardGroupFeature(GroupFeature groupFeature, FeatureProfile profile) {
    super(groupFeature);
    this.profile = profile;
  }

  @Override
  public FeatureProfile profile() {
    return this.profile;
  }
}
