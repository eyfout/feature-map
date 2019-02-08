package ht.eyfout.map.element;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;

public interface FeatureSupporter {

  void addFeature(Feature feature);

  void removeFeature(Feature feature);

  <T extends FeatureOperation> T operations(Feature feature);

  boolean hasFeature(Feature feature);
}
