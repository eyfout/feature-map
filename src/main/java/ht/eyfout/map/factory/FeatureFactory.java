package ht.eyfout.map.factory;

import ht.eyfout.map.features.FeatureProfile;
import ht.eyfout.map.features.GroupFeature;
import java.util.function.Function;

public class FeatureFactory <P extends GroupFeature> {

  FeatureProfile profile;
  private Function<GroupFeature, P> pageFunc;

  protected FeatureFactory(Function<GroupFeature, P> pageFunc) {
    this.pageFunc = pageFunc;
    profile = pageFeature().profile();
  }

  protected FeatureFactory() {
  }

  public static <P extends GroupFeature> FeatureFactory<P> create(
      Function<GroupFeature, P> pageFeatureFunc) {
    return new FeatureFactory<P>(pageFeatureFunc);
  }

  public P pageFeature(P pageFeature) {
    return pageFunc.apply(pageFeature);
  }

  public GroupFeature pageFeature() {
    return pageFeature(null);
  }

  public FeatureProfile profile() {
    return profile;
  }
}
