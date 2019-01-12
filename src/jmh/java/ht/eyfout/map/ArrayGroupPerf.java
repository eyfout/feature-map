package ht.eyfout.map;

import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.GroupDataMart;
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart;
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart.ArrayGroupDataMartBuilder;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.guice.GuiceInstance;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
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
public class ArrayGroupPerf {

  private Group groupElement;
  private Group groupBackedByCopy;

  @Setup
  public void doSetup() {
    DataMartFactory martFactory = GuiceInstance.get(DataMartFactory.class);
    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class);

    GroupDataMart original =
        martFactory
            .<ArrayGroupDataMart, ArrayGroupDataMartBuilder>create(ArrayGroupDataMart.class)
            .build();
    groupElement = factory.group(original);
    for (int i = 0; i < 100; i++) {
      groupElement.putScalarValue("key-" + i, i);
    }

    groupBackedByCopy = factory.group(original.<GroupDataMart>copy());
  }

  @Benchmark
  public Integer getValueFromMart() {
    return groupElement.<Integer>getScalarValue("key-3");
  }

  @Benchmark
  public Integer getValueFromCopyMart() {
    return groupBackedByCopy.<Integer>getScalarValue("key-3");
  }

  @Benchmark
  public void putValueFromMart(Blackhole bh) {
    groupElement.putScalarValue("key-3", 1);
    bh.consume( groupElement );
  }

  @Benchmark
  public void putValueInCopyMart(Blackhole bh) {
    groupBackedByCopy.putScalarValue("key-3", 1);
    bh.consume(groupBackedByCopy);
  }
}
