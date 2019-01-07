package ht.eyfout.map.element.internal;

import ht.eyfout.map.features.FeatureForward;
import ht.eyfout.map.features.FeatureProfile;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class RuntimeContext {
  private Map<FeatureProfile, Object> runtimeData = new ConcurrentHashMap<>();
  private Map<Object, RuntimeContext> contextTree = new ConcurrentHashMap<>();

  private RuntimeContext() {}

  public static RuntimeContext create() {
    return new RuntimeContext();
  }

  public <T> T data(FeatureForward feature) {
    FeatureProfile profile = feature.profile();
    T result = (T) runtimeData.get(profile);
    if (null == result) {
      result = (T)feature.init(this);
      runtimeData.put(profile, result);
    }
    return result;
  }

  public <T> RuntimeContext get(T id) {
    RuntimeContext context = contextTree.get(id);
    if (null == context) {
      context = new RuntimeContext();
      contextTree.put(id, context);
    }
    return context;
  }
}
