package ht.eyfout.map;

import com.google.common.collect.ImmutableMap;
import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart;
import ht.eyfout.map.data.storage.map.MapGroupDataMart;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.guice.GuiceInstance;
import java.util.HashMap;
import java.util.Map;
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

  static final Map<String, Class<? extends GroupDataMart>> providers =
      ImmutableMap.of("map", MapGroupDataMart.class, "array", ArrayGroupDataMart.class);

  @Param({"array", "map"})
  String DataProvider;

  private Group groupElement;

  @Setup
  public void doSetup() {
    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class);
    groupElement =
        factory.group(
            GuiceInstance.get(DataMartFactory.class)
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

}
