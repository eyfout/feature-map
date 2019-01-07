package ht.eyfout.map.features;

import ht.eyfout.map.element.internal.RuntimeContext;
import java.util.Optional;

public abstract class FeatureForward<F extends Feature> {
  private Optional<F> feature;

  FeatureForward(F feature) {
    this.feature = Optional.ofNullable(feature);
  }

  protected Optional<F> next() {
    return feature;
  }

  public abstract FeatureProfile profile();

  public Object init(RuntimeContext context){
    throw new UnsupportedOperationException();
  }
}
