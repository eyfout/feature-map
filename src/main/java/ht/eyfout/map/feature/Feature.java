package ht.eyfout.map.feature;

public enum Feature implements FeatureDescriptor {
  MESSAGING,
  DELTA_STORE(MESSAGING);

  FeatureState state;
  Feature[] dependsOn;

  Feature(Feature... dependsOn) {
    this(FeatureState.STATELESS, dependsOn);
  }

  Feature(FeatureState state, Feature... dependsOn) {
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
}
