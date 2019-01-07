package ht.eyfout.map.feature;

import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.runtime.RuntimeContext;

public abstract class GroupFeature extends FeatureForward<GroupFeature>  {
  protected GroupFeature(GroupFeature groupFeature) {
    super(groupFeature);
  }

  public <T> T putScalarValue(String name, T value, Group element, RuntimeContext context){
    return next().map((feature) -> feature.putScalarValue(name, value, element, context)).orElse(value);
  }

  public <T> T getScalarValue(String name, T value, Group element, RuntimeContext context){
    return next().map((feature) -> feature.getScalarValue(name, value, element, context)).orElse(value);
  }



}
