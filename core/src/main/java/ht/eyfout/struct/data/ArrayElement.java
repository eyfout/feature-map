package ht.eyfout.struct.data;

import ht.eyfout.struct.data.visitor.ElementVisitor;
import java.util.Map;
import java.util.function.Function;

public class ArrayElement implements Element<Integer> {

  Accessor values;
  private ElementKeyResolver keyResolver;
  private Map<String, Integer> nameToIntMap;

  public ArrayElement(ElementKeyResolver keyResolver) {
    this(keyResolver, new Accessor());
  }

  private ArrayElement(ElementKeyResolver keyResolver, Accessor values) {
    this.keyResolver = keyResolver;
    this.values = values;
  }

  private ArrayElement(ArrayElement original) {
    keyResolver = original.keyResolver;
    values = original.values.copy();
  }

  @Override
  public void putElement(Integer index, Element<Integer> element) {
    if (element instanceof ArrayElement) {
      ArrayElement arrayElement = (ArrayElement) element;
      values.putAccessor(keyResolver.getIndexFor(index), arrayElement);
    }
  }

  @Override
  public <V> void put(Integer index, V value) {
    values.put(keyResolver.getIndexFor(index), value);
  }

  @Override
  public <V> V get(Integer index) {
    return values.get(keyResolver.getIndexFor(index));
  }

  @Override
  public Element<Integer> getElement(Integer index) {
    Element element = values.getAccessor(keyResolver.getIndexFor(index));
    if (element == null) {
      return null;
    }
    return element;
  }

  @Override
  public Element<Integer> copy() {
    return new ArrayElement(this);
  }

  @Override
  public Function<String, Integer> keysFunction() {
    return keyResolver::getIndexFor;
  }

  @Override
  public <V> V accept(ElementVisitor<V, Integer> visitor) {
    visitor.visitStart(this);
    return visitor.visitEnd(this);
  }

  private static class Accessor {
    private static final Object[] NO_VALUE = new Object[0];
    private static final Element[] NO_EMBEDDED_ACCESSOR = new Element[0];

    Object[] values = NO_VALUE;
    Element[] structs = NO_EMBEDDED_ACCESSOR;

    Accessor() {
      // do nothing
    }

    private Accessor(Accessor original) {
      values = Elements.expandArray(original.values, original.values.length);
      structs = new Element[original.structs.length];
      int index = 0;
      for (Element accessor : original.structs) {
        structs[index++] = accessor.copy();
      }
    }

    Element getAccessor(Integer index) {
      return structs[index];
    }

    void putAccessor(Integer index, Element accessor) {
      structs = Elements.expandArray(structs, index);
      structs[index] = accessor;
    }

    public <V> void put(Integer index, V value) {
      values = Elements.expandArray(values, index);
      values[index] = value;
    }

    public <V> V get(Integer index) {
      return (V) values[index];
    }

    public Accessor copy() {
      return new Accessor(this);
    }
  }
}
