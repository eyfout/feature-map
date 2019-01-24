package ht.eyfout.map.element;

public interface Group extends FeatureSupporter, FeatureSupporter.Container<String>, Element {

  /**
   * Seta a value
   *
   * @param name
   * @param value
   * @param <T>
   */
  <T> void putScalarValue(String name, T value);

  <T> T getScalarValue(String name);

  void putGroup(String name, Group group);
}
