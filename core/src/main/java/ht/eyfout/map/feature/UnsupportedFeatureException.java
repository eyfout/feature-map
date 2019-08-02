package ht.eyfout.map.feature;

public class UnsupportedFeatureException extends RuntimeException {

  public UnsupportedFeatureException(FeatureDescriptor descriptor) {
    super(descriptor.toString());
  }

  public UnsupportedFeatureException(FeatureDescriptor descriptor, Throwable cause) {
    super(descriptor.toString(), cause);
  }

  public UnsupportedFeatureException(Throwable cause) {
    super(cause);
  }

  public UnsupportedFeatureException(
      FeatureDescriptor descriptor,
      Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(descriptor.toString(), cause, enableSuppression, writableStackTrace);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}
