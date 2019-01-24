package ht.eyfout.map.data.storage.visitor.internal;

import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.visitor.DataStorageVisitor;
import ht.eyfout.map.data.storage.visitor.VisitorResult;
import ht.eyfout.map.element.Element;
import ht.eyfout.map.element.Group;
import ht.eyfout.map.element.Scalar;
import ht.eyfout.map.element.internal.GroupElement;
import ht.eyfout.map.element.visitor.ElementVisitor;
import ht.eyfout.map.scalar.ScalarReference;
import java.util.Stack;

public class ElementDataStorageVisitor implements DataStorageVisitor {
  private final Stack<Element> stack;
  private final ElementVisitor visitor;

  public ElementDataStorageVisitor(Element groupElement, ElementVisitor visitor) {
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
  public void post(GroupDataStorage storage) {
    Group groupElement = (Group) stack.pop();
    visitor.post(groupElement);
  }

  @Override
  public <T> VisitorResult visit(String name, T value) {
    GroupElement groupElement = (GroupElement) stack.peek();
    Scalar scalar = ScalarReference.getScalar(name, groupElement);
    return visitor.visit(name, scalar);
  }

  @Override
  public <T> T result() {
    return visitor.result();
  }
}
