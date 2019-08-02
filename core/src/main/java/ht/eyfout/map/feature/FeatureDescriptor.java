package ht.eyfout.map.feature;

public interface FeatureDescriptor {
  String name();

  FeatureDescriptor[] dependsOn();

  int ordinal();

  default int rank() {
    return 1 << ordinal();
  }

  FeatureState state();

  enum FeatureState {
    STATEFUL,
    STATELESS
  }
}
