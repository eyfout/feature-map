package ht.eyfout.map.scalar;

import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ScalarReference<T, R> implements Scalar<R> {

  private Container container;
  private T ref;
  private Supplier<R> gettterFunc;
  private Consumer<R> setterFunc;

  private ScalarReference(T ref, Container container) {
    this.container = container;
    this.ref = ref;
    gettterFunc = ScalarFunctions.get(ref, container);
    setterFunc = ScalarFunctions.set(ref, container);
  }

  public static <T, R> Scalar<R> getScalar(T ref, Container container) {
    return new ScalarReference<>(ref, container);
  }

  @Override
  public void set(R value) {
    setterFunc.accept(value);
  }

  @Override
  public R get() {
    return gettterFunc.get();
  }

  @Override
  public void addFeature(Feature feature) {
    container.addFeature(ref, feature);
  }

  @Override
  public void removeFeature(Feature feature) {
    container.removeFeature(ref, feature);
  }

  @Override
  public <T extends FeatureOperation> T operations(Feature feature) {
    return (T) container.operations(ref, feature);
  }

  @Override
  public boolean hasFeature(Feature feature) {
    return container.hasFeature(ref, feature);
  }
}
