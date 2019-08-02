package ht.eyfout.map.element.visitor;

import ht.eyfout.map.visitor.VisitorResult;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;

public interface ElementVisitor<R> {
  void pre(Group element);

  R post(Group element);

  VisitorResult visit(String name, Scalar element);
}
