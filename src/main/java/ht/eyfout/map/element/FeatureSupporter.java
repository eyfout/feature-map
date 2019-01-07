package ht.eyfout.map.element;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.runtime.data.RuntimeData;

public interface FeatureSupporter {
  void addFeature(Feature feature);

  void removeFeature(Feature feature);

  <T extends RuntimeData> T runtimeData(Feature feature);
}
