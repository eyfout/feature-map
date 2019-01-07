package ht.eyfout.map.element;

import ht.eyfout.map.feature.Feature;

public interface FeatureSupporter {
  void addFeature(Feature feature);

  void removeFeature(Feature feature);
}
