package ht.eyfout.map.factory;

import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.ScalarFeature;
import java.util.function.Function;

public class FeatureFactory<P extends GroupFeature, S extends ScalarFeature> {

  private FeatureProfile profile;
  private Function<GroupFeature, P> groupFunc;
  private Function<ScalarFeature, S> scalarFunc;

  private FeatureFactory(
      Function<GroupFeature, P> groupFunc, Function<ScalarFeature, S> scalarFunc) {
    this.scalarFunc = scalarFunc;
    this.groupFunc = groupFunc;
    profile = groupFeature().profile();
  }

  protected FeatureFactory() {}

  public static <T> Function<FeatureFactory, FeatureDefinition> elementFunctions(Class<T> clazz) {
    if (Group.class.isAssignableFrom(clazz)) {
      return FeatureFactory::groupFeature;
    } else if (Scalar.class.isAssignableFrom(clazz)) {
      return FeatureFactory::scalarFeature;
    }
    throw new IllegalArgumentException();
  }

  public static <P extends GroupFeature> FeatureFactory<P, ScalarFeature> create(
      Function<GroupFeature, P> groupFeatureFunc) {
    return create(
        groupFeatureFunc,
        (scalarFeature) -> scalarFeature);
  }

  public static <P extends GroupFeature, S extends ScalarFeature> FeatureFactory<P, S> create(
      Function<GroupFeature, P> groupFeatureFunc, Function<ScalarFeature, S> scalarFeatureFunc) {
    return new FeatureFactory<P, S>(groupFeatureFunc, scalarFeatureFunc);
  }

  public P groupFeature(GroupFeature groupFeature) {
    return groupFunc.apply(groupFeature);
  }

  public final P groupFeature() {
    return groupFeature(null);
  }

  public S scalarFeature(ScalarFeature scalarFeature) {
    return scalarFunc.apply(scalarFeature);
  }

  public final ScalarFeature scalarFeature() {
    return scalarFeature(null);
  }

  public FeatureProfile profile() {
    return profile;
  }
}
