package ht.eyfout.map.element;

public interface Element {
  default <T> T accept(ElementVisitor visitor){
    throw new UnsupportedOperationException();
  }
}
