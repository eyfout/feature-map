package ht.eyfout.map.element.visitor;

import ht.eyfout.map.data.storage.visitor.VisitorResult;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;

public interface ElementVisitor {
  void pre(Group element);

  void post(Group element);

  VisitorResult visit(String name, Scalar element);

  <T> T result();
}
