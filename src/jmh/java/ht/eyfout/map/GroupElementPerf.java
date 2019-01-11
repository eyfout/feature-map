package ht.eyfout.map;

import com.google.common.collect.ImmutableMap;
import ht.eyfout.map.data.storage.DataStoreFactory;
import ht.eyfout.map.data.storage.GroupDataStore;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStore;
import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.guice.GuiceInstance;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@Measurement(timeUnit = TimeUnit.NANOSECONDS, iterations = 2)
@Warmup(iterations = 2, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class GroupElementPerf {

  static final java.util.Map<String, Class<? extends GroupDataStore>> providers =
      ImmutableMap.of("map", MapGroupDataStore.class, "array", ArrayGroupDataStore.class);

  @Param({"array", "map"})
  String DataProvider;

  private Group groupElement;

  @Setup
  public void doSetup() {
    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class);
    groupElement =
        factory.group(
            GuiceInstance.get(DataStoreFactory.class)
                .create(providers.get(DataProvider.toLowerCase()))
                .build());
    groupElement.putScalarValue("key", 911);
  }

  @Benchmark
  public Group putScalarValue() {
    groupElement.putScalarValue("key-1", "value-1");
    return groupElement;
  }

  @Benchmark
  public Integer getScalarValue_ExistingValue() {
    return groupElement.getScalarValue("key");
  }

  @Benchmark
  public Object getScalarValue_NullValue() {
    return groupElement.getScalarValue("key-NotNull");
  }

  @Measurement(timeUnit = TimeUnit.NANOSECONDS, iterations = 2)
  @Warmup(iterations = 2, timeUnit = TimeUnit.NANOSECONDS)
  @Fork(2)
  @BenchmarkMode({Mode.AverageTime})
  @State(Scope.Benchmark)
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  public static class Map {
    private java.util.Map<String, Object> map = new HashMap<>();

    @Setup
    public void doSetup() {
      map.put("key", 911);
    }

    @Benchmark
    public Object map_GetExistingValue() {
      return map.get("key");
    }

    @Benchmark
    public Object map_putValue() {
      return map.put("key-1", "value-1");
    }
  }
}
