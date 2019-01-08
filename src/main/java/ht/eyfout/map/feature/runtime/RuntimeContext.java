package ht.eyfout.map.feature.runtime;

import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.runtime.visitor.RuntimeContextVisitor;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class RuntimeContext {
  private Optional<RuntimeContext> parent;
  private Supplier<Map<Object, Object>> runtimeDataSupplier;

  private RuntimeContext() {
    final Map<Object, Object> runtimeData = new ConcurrentHashMap<>();
    parent = Optional.empty();
    runtimeDataSupplier = () -> runtimeData;
  }

  private RuntimeContext(RuntimeContext parent, Supplier<Map<Object, Object>> runtimeDataSupplier) {
    this.parent = Optional.ofNullable(parent);
    this.runtimeDataSupplier = runtimeDataSupplier;
  }

  public static RuntimeContext create() {
    return new RuntimeContext();
  }

  @SuppressWarnings("unchecked")
  public <T> T data(final FeatureDefinition definition) {
    FeatureProfile profile = definition.profile();
    T result = (T) runtimeDataSupplier.get().get(profile);
    if (null == result) {
      result = (T) definition.init(this);
      runtimeDataSupplier.get().put(profile, result);
      parent.map((context) -> context.data(definition));
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public RuntimeContext get(final String id) {
    return new RuntimeContext(
        this, new NestedRuntimeContextSupplier<String>(runtimeDataSupplier, id));
  }

  public Optional<RuntimeContext> parent() {
    return parent;
  }

  public void visit(RuntimeContextVisitor visitor, FeatureDefinition feature) {}

  private final class NestedRuntimeContextSupplier<T> implements Supplier<Map<Object, Object>> {
    private final Supplier<Map<Object, Object>> parent;
    private final T name;
    private Map<Object, Object> contextMap;

    private NestedRuntimeContextSupplier(Supplier<Map<Object, Object>> parent, T name) {
      this.parent = parent;
      this.name = name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<Object, Object> get() {
      if (null == contextMap) {
        contextMap = (Map<Object, Object>) parent.get().get(name);
        if (null == contextMap) {
          contextMap = new ConcurrentHashMap<>();
          parent.get().put(name, contextMap);
        }
      }
      return contextMap;
    }
  }
}
