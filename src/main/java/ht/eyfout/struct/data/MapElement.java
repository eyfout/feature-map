package ht.eyfout.struct.data;

import ht.eyfout.struct.data.visitor.ElementVisitor;
import java.util.HashMap;
import java.util.function.Function;

public class MapElement implements Element<String> {

  private static final Function<String, String> mappingFunction = it -> it;
  private final Element<Integer> intElement;
  private final Function<String, Integer> stringMappingFunc;

  public MapElement() {
    this(new ArrayElement(new ElementKeyResolver(new HashMap<>())));
  }

  private MapElement(final Element<Integer> intElement) {
    this.intElement = intElement;
    stringMappingFunc = intElement.keysFunction();
  }

  @Override
  public void putElement(String s, Element<String> element) {
    if (element instanceof MapElement) {
      intElement.putElement(stringMappingFunc.apply(s), ((MapElement) element).intElement);
    }
  }

  @Override
  public <V> void put(String s, V value) {
    intElement.put(stringMappingFunc.apply(s), value);
  }

  @Override
  public <V> V get(String s) {
    return intElement.get(stringMappingFunc.apply(s));
  }

  @Override
  public Element<String> getElement(String s) {
    return new MapElement(intElement.getElement(stringMappingFunc.apply(s)));
  }

  @Override
  public Element<String> copy() {
    return new MapElement(intElement.copy());
  }

  @Override
  public Function<String, String> keysFunction() {
    return mappingFunction;
  }

  @Override
  public <V> V accept(ElementVisitor<V, String> visitor) {
    visitor.visitStart(this);
    return visitor.visitEnd(this );
  }
}
