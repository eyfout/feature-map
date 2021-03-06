package ht.eyfout.map.data.storage.visitor.internal;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.ScalarDataStorage;
import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;
import ht.eyfout.map.visitor.VisitorResult;
import ht.eyfout.map.element.Element;
import ht.eyfout.map.element.visitor.ElementVisitor;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.GroupElement;
import java.util.Stack;

public class ElementDataStorageVisitor<R> implements DataStorageVisitor<R> {
  private final Stack<Element> stack;
  private final ElementVisitor<R> visitor;

  public ElementDataStorageVisitor(Element groupElement, ElementVisitor<R> visitor) {
    stack = new Stack<>();
    stack.push(groupElement);
    this.visitor = visitor;
  }

  @Override
  public void pre(GroupDataStorage storage) {
    Group groupElement = (Group) stack.peek();
    visitor.pre(groupElement);
  }

  @Override
  public R post(GroupDataStorage storage) {
    Group groupElement = (Group) stack.pop();
    return visitor.post(groupElement);
  }


  @Override
  public VisitorResult visit(String name, ScalarDataStorage storage) {
    GroupElement groupElement = (GroupElement) stack.peek();
    Scalar scalar = groupElement.getScalar(name);
    return visitor.visit(name, scalar);
  }

}
