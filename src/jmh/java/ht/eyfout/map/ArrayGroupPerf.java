package ht.eyfout.map;

import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage.ArrayGroupDataStorageBuilder;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.ArrayGroupVersion;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.test.artifacts.Seed;
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

  protected ArrayGroupVersion version;
  private Group groupElement;
  private Group groupBackedByCopy;
  private GroupDataStorage mart;

  public ArrayGroupPerf() {
    version = ArrayGroupVersion.FUNCTION;
  }

  @Setup
  public void doSetup() {
    DataStorageBuilderFactory martFactory = GuiceInstance.get(DataStorageBuilderFactory.class);
    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class);

    GroupDataStorage original =
        martFactory
            .<ArrayGroupDataStorage, ArrayGroupDataStorageBuilder>create(ArrayGroupDataStorage.class)
            .version(version)
            .build();
    groupElement = factory.group(original);
    Seed.seedGroupElement(groupElement, 100, "key-");
    groupBackedByCopy = factory.group(original.<GroupDataStorage>copy());
    mart = original;
  }

  @Benchmark
  public Integer getValue() {
    return groupElement.<Integer>getScalarValue("key-3");
  }

  @Benchmark
  public Integer getValueInCopyMart() {
    return groupBackedByCopy.<Integer>getScalarValue("key-3");
  }

  @Benchmark
  public void putValue(Blackhole bh) {
    groupElement.putScalarValue("key-3", 1);
    bh.consume(groupElement);
  }

  @Benchmark
  public void putValueInCopyMart(Blackhole bh) {
    groupBackedByCopy.putScalarValue("key-3", 1);
    bh.consume(groupBackedByCopy);
  }

  @Benchmark
  public GroupDataStorage copy() {
    return mart.<GroupDataStorage>copy();
  }

  @Benchmark
  public void setScalarValue(Blackhole bh) {
    Scalar<Integer> scalar = groupElement.getScalar("key-3");
    scalar.set(101);
    bh.consume(scalar);
  }

  @Benchmark
  public void setAndOverrideScalarValue(Blackhole bh) {
    Scalar<Integer> scalar = groupElement.getScalar("key-3");
    scalar.set(101);
    scalar.set(300);
    bh.consume(scalar);
  }

  @Benchmark
  public void setScalarValueOnCopy(Blackhole bh) {
    Scalar<Integer> scalar = groupBackedByCopy.getScalar("key-3");
    scalar.set(101);
    bh.consume(scalar);
  }

  @Benchmark
  public void setAndOverrideScalarValueOnCopy(Blackhole bh) {
    Scalar<Integer> scalar = groupBackedByCopy.getScalar("key-3");
    scalar.set(101);
    scalar.set(300);
    bh.consume(scalar);
  }

  public static class ArrayGroupInt extends ArrayGroupPerf {
    public ArrayGroupInt() {
      version = ArrayGroupVersion.INT;
    }
  }

}
