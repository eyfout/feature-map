package ht.eyfout.struct.data;

import ht.eyfout.struct.data.visitor.ElementVisitor;
import java.util.function.Function;

public interface Element<Key> {

  /**
   * Puts a structured element
   * @param key
   * @param element
   */
  void putElement(Key key, Element<Key> element);

  /**
   * Puts the value at the specified value
   * @param key
   * @param value
   * @param <V>
   */
  <V> void put(Key key, V value);

  /**
   * Retrieves the value at the specified key
   * @param key
   * @param <V>
   * @return
   */
  <V> V get(Key key);

  /**
   * Retrieve the strcutured element specified by the key
   * @param key
   * @return
   */
  Element<Key> getElement(Key key);

  /**
   * Created a copy of this {@link Element}
   * @return
   */
  Element<Key> copy();

  /**
   * Function to access the keys by their toString representation
   * @return
   */
  Function<String, Key> keysFunction();


  <V> V accept(ElementVisitor<V, Key> visitor);
}
