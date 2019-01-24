package ht.eyfout.map.element;

import ht.eyfout.map.element.visitor.ElementVisitor;

public interface Element {
  default <T> T accept(ElementVisitor visitor) {
    throw new UnsupportedOperationException();
  }
}
