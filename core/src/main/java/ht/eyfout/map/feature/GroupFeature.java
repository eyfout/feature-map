package ht.eyfout.map.feature;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.feature.runtime.RuntimeContext;

public abstract class GroupFeature extends FeatureForward<GroupFeature> {
  protected GroupFeature(GroupFeature groupFeature) {
    super(groupFeature);
  }

  public <T> T putScalarValue(
      final String name, final T value, final Group element, final RuntimeContext context) {
    return next()
        .map((feature) -> feature.putScalarValue(name, value, element, context))
        .orElse(value);
  }

  public <T> T getScalarValue(
      final String name, final T value, final Group element, final RuntimeContext context) {
    return next()
        .map((feature) -> feature.getScalarValue(name, value, element, context))
        .orElse(value);
  }

  public <T> ScalarDataStorage<T> getScalar(
      final String name,
      final ScalarDataStorage<T> scalar,
      final Group element,
      final RuntimeContext context) {
    return next()
        .map((feature) -> feature.getScalar(name, scalar, element, context))
        .orElse(scalar);
  }

  public GroupDataStorage putGroup(
      final String name,
      final GroupDataStorage groupValue,
      final Group element,
      final RuntimeContext context) {
    return next()
        .map((feature) -> feature.putGroup(name, groupValue, element, context))
        .orElse(groupValue);
  }
}
