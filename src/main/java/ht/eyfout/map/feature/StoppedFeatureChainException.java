package ht.eyfout.map.feature;

import ht.eyfout.map.features.FeatureForward;

public class StoppedFeatureChainException extends RuntimeException {

  public StoppedFeatureChainException(FeatureForward feature) {
    super(feature.profile().toString());
  }

  public StoppedFeatureChainException(FeatureForward feature, Throwable cause) {
    super(feature.toString(), cause);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
