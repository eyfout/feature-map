package ht.eyfout.struct.data.visitor;

import ht.eyfout.struct.data.Element;

public interface ElementVisitor<V, Key> {

  /**
   * Start visiting element
   *
   * @param elem
   */
  void visitStart(Element<Key> elem);

  /**
   * Visit values within an element
   *
   * @param key
   * @param value
   * @param <Value>
   * @return
   */
  <Value> VisitorResult visit(Key key, Value value);

  /**
   * Start visiting embedded element
   *
   * @param key
   * @param value
   * @return
   */
  VisitorResult visitStart(Key key, Element<Key> value);

  /**
   * Finish visiting embedded element
   *
   * @param key
   * @param value
   * @return
   */
  VisitorResult visitEnd(Key key, Element<Key> value);

  /**
   * Finish visiting element
   *
   * @param elem
   * @return
   */
  V visitEnd(Element<Key> elem);

  interface IntElementVisitor<V> extends ElementVisitor<V, Integer> {}

  interface StringElementVisitor<V> extends ElementVisitor<V, String> {}
}
