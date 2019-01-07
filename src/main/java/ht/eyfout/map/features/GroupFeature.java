package ht.eyfout.map.features;

import ht.eyfout.map.element.internal.RuntimeContext;

public abstract class GroupFeature extends FeatureForward<GroupFeature> implements Feature {
  protected GroupFeature(GroupFeature groupFeature) {
    super(groupFeature);
  }

  public <T> T putScalarValue(String name, T value, RuntimeContext context){
    System.out.println( "put " +  profile() );
    return next().map((feature) -> feature.putScalarValue(name, value, context)).orElse(value);
  }

  public <T> T getScalarValue(String name, T value, RuntimeContext context){
    System.out.println( "get " + profile() );
    return next().map((feature) -> feature.getScalarValue(name, value, context)).orElse(value);
  }

}
