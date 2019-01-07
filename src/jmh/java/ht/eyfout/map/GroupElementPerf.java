package ht.eyfout.map;

import ht.eyfout.map.element.Group;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.guice.PerfModule;
import java.util.HashMap;
import java.util.Map;
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

@Measurement(timeUnit = TimeUnit.NANOSECONDS, iterations = 2)
@Warmup(iterations = 2, timeUnit = TimeUnit.NANOSECONDS)
@Fork(2)
@BenchmarkMode({Mode.AverageTime})
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class GroupElementPerf {

  private Group element;
  private Map<String, Object> map;

  @Setup
  public void doSetup(){
    map = new HashMap<>();
    map.put("key", 911);

    ElementMapFactory factory = PerfModule.get(ElementMapFactory.class);
    element = factory.group();
    element.putScalarValue("key", 911);
  }

  @Benchmark
  public Object map_GetExistingValue(){
    return map.get("key");
  }

  @Benchmark
  public Object map_putValue(){
    return map.put("key-1", "value-1");
  }

  @Benchmark
  public Group putScalarValue(){
    element.putScalarValue("key-1", "value-1");
    return element;
  }

  @Benchmark
  public Integer getScalarValue_ExistingValue(){
    return element.getScalarValue("key");
  }

  @Benchmark
  public Object getScalarValue_NullValue(){
    return element.getScalarValue("key-NotNull");
  }
}
