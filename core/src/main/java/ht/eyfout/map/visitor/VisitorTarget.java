package ht.eyfout.map.visitor;

public interface VisitorTarget<T> {
  VisitorResult visit(String name, T value);
  VisitorResult visit(int name, T value);
}
