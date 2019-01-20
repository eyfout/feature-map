package ht.eyfout.map.element;

import ht.eyfout.map.data.storage.visitor.VisitorResult;

public interface ElementVisitor {
  void pre(Group element);

  void post(Group element);

  VisitorResult visit(String name, Scalar element);
  <T> T result();
}
