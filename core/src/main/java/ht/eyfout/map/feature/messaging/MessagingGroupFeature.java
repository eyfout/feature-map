package ht.eyfout.map.feature.messaging;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;

public class MessagingGroupFeature extends GroupFeature {

  public MessagingGroupFeature(GroupFeature groupFeature) {
    super(groupFeature);
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.MESSAGING);
  }
}
