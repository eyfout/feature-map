package ht.eyfout.map.feature;

import java.util.Optional;

public abstract class FeatureForward<F extends FeatureDefinition> implements FeatureDefinition {
  private F feature;

  FeatureForward(F feature) {
    this.feature = feature;
  }

  protected Optional<F> next() {
    return Optional.ofNullable(feature);
  }
}
