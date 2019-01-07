package ht.eyfout.map.feature;

import java.util.Optional;

public abstract class FeatureForward<F extends FeatureDefinition>  implements FeatureDefinition {
  private Optional<F> feature;

  FeatureForward(F feature) {
    this.feature = Optional.ofNullable(feature);
  }

  protected Optional<F> next() {
    return feature;
  }

}
