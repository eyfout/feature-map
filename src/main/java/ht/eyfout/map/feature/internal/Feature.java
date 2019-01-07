package ht.eyfout.map.feature.internal;

import ht.eyfout.map.feature.FeatureDescriptor;

public enum Feature implements FeatureDescriptor {
  DICTIONARY(FeatureState.STATEFUL);

  FeatureState state;
  FeatureDescriptor[] dependsOn;
  Feature(FeatureState state, FeatureDescriptor...dependsOn) {
    this.state = state;
    this.dependsOn = dependsOn;
  }

  @Override
  public FeatureDescriptor[] dependsOn() {
    return dependsOn;
  }

  @Override
  public FeatureState state() {
    return state;
  }

  @Override
  public int rank() {
    return 1 << (ht.eyfout.map.feature.Feature.values().length + ordinal());
  }
}
