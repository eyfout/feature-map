package ht.eyfout.map;

import com.google.common.collect.ImmutableMap;
import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage.ArrayGroupDataStorageBuilder;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage.MapGroupDataStorageBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.test.artifacts.Seed;
import ht.guice.GuiceInstance;
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
import org.openjdk.jmh.infra.Blackhole;

@Measurement(timeUnit = TimeUnit.NANOSECONDS, iterations = 2)
@Warmup(iterations = 2, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class GroupElementPerf {

  static final Map<String, Class<? extends DataStorageBuilder>> providers =
      ImmutableMap.of(
          "map", MapGroupDataStorageBuilder.class, "array", ArrayGroupDataStorageBuilder.class);

  @Param({"array", "map"})
  String DataProvider;

  private Group groupElement;

  @Setup
  public void doSetup() {
    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class);
    groupElement =
        factory.group(
            GuiceInstance.get(DataStorageBuilderFactory.class)
                .create(providers.get(DataProvider.toLowerCase()))
                .build());
    Seed.seedGroupElement(groupElement, 30, "key#");
  }

  @Benchmark
  public Group putScalarValue() {
    groupElement.putScalarValue("key#1", "value-1");
    return groupElement;
  }

  @Benchmark
  public void setScalarValue(Blackhole bh) {
    Scalar<Integer> scalar = groupElement.getScalar("key#3");
    scalar.set(101);
    bh.consume(scalar);
  }

  @Benchmark
  public void setAndOverrideScalarValue(Blackhole bh) {
    Scalar<Integer> scalar = groupElement.getScalar("key#3");
    scalar.set(101);
    scalar.set(300);
    bh.consume(scalar);
  }

  @Benchmark
  public Integer getScalarValue_ExistingValue() {
    return groupElement.getScalarValue("key#3");
  }

  @Benchmark
  public Object getScalarValue_NullValue() {
    return groupElement.getScalarValue("key-NotNull");
  }
}
