package ht.eyfout.map.factory;

import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import java.util.function.Function;

public class FeatureFactory <P extends GroupFeature> {

  public static <F extends FeatureDefinition> Function<FeatureFactory, F> functions( Class clazz){
    if( clazz.isAssignableFrom(Group.class) ){
      return (ff)-> (F)ff.groupFeature();
    }
    throw new UnsupportedOperationException();
  }


  private FeatureProfile profile;
  private Function<GroupFeature, P> groupFunc;

  protected FeatureFactory(Function<GroupFeature, P> groupFunc) {
    this.groupFunc = groupFunc;
    profile = groupFeature().profile();
  }

  protected FeatureFactory() {
  }

  public static <P extends GroupFeature> FeatureFactory<P> create(
      Function<GroupFeature, P> groupFeatureFunc) {
    return new FeatureFactory<P>(groupFeatureFunc);
  }

  public P groupFeature(P groupFeature) {
    return groupFunc.apply(groupFeature);
  }

  public P groupFeature() {
    return groupFeature(null);
  }

  public FeatureProfile profile() {
    return profile;
  }
}
