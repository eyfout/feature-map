package ht.eyfout.struct.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode({Mode.AverageTime})
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayElementPerf {

  Element<Integer> intElement;
  Map<Integer, String> map;

  @Setup
  public void doSetup() {
    Map<String, Integer> keysToIntMap =
        new HashMap<String, Integer>() {
          {
            put("pyLabel", 1);
            put("pyName", 4);
            put("pyLastElement", 12_200);
          }
        };

    intElement = new ArrayElement(new ElementKeyResolver(keysToIntMap));
    map = new HashMap<>();

    for (int i = 0; i < keysToIntMap.get("pyLastElement"); i++) {
      map.put(i, "String_" + i);
      intElement.put(i, "String_" + i);
    }
  }

  public void put(Blackhole bh) {
    intElement.put(2, "Benchmark");
  }

  public String putOnMap(Blackhole bh) {
    return map.put(2, "Benchmark");
  }

  @Benchmark
  public Element<Integer> copy() {
    return intElement.copy();
  }

  public Map<Integer, String> copyMap() {
    return new HashMap<>(map);
  }

  public String get() {
    return intElement.get(1);
  }

  public String getFromMap() {
    return map.get(1);
  }

  public String getByName() {
    return intElement.get(intElement.keysFunction().apply("pyLabel"));
  }
}
