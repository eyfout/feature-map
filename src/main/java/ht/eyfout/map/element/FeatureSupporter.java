package ht.eyfout.map.element;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;

public interface FeatureSupporter {
  /**
   * Add feature
   *
   * @param feature
   */
  void addFeature(Feature feature);

  /**
   * Remove feature
   *
   * @param feature
   */
  void removeFeature(Feature feature);

  <T extends FeatureOperation> T operations(Feature feature);

  boolean hasFeature(Feature feature);

  interface Container<T> {

    /**
     * Add feature to nested {@link FeatureSupporter}.
     *
     * @param ref
     * @param feature
     */
    void addFeature(T ref, Feature feature);

    /**
     * Remove feature from nested {@link FeatureSupporter}.
     *
     * @param ref
     * @param feature
     */
    void removeFeature(T ref, Feature feature);

    /**
     * Retrieve operations supported by nested {@link FeatureSupporter}.
     *
     * @param ref
     * @param feature
     * @param <O>
     * @return
     */
    <O extends FeatureOperation> O operations(T ref, Feature feature);

    /**
     * Determine if a {@link FeatureSupporter} has a feature.
     *
     * @param ref
     * @param feature
     * @return
     */
    boolean hasFeature(T ref, Feature feature);
  }
}
