package ht.eyfout.map.feature;

public class StoppedFeatureChainException extends RuntimeException {
  private static final String ERR_MESSAGE = "%s set to %s by feature: %s.";

  public StoppedFeatureChainException(FeatureDefinition feature, String elementName, Object value) {
    super(String.format(ERR_MESSAGE, elementName, value, feature.profile().toString()));
  }

  public StoppedFeatureChainException(
      FeatureDefinition feature, String elementName, Object value, Throwable cause) {
    super(String.format(ERR_MESSAGE, elementName, value, feature.profile().toString()), cause);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
