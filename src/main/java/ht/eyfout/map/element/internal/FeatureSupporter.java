package ht.eyfout.map.element.internal;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.runtime.data.RuntimeData;
import java.util.Optional;

public interface FeatureSupporter extends ht.eyfout.map.element.FeatureSupporter {

  @Override
  default void addFeature(Feature descriptor) {
    addFeature((FeatureDescriptor) descriptor);
  }

  void addFeature(FeatureDescriptor descriptor);

  @Override
  default void removeFeature(Feature descriptor) {
    removeFeature((FeatureDescriptor) descriptor);
  }

  void removeFeature(FeatureDescriptor descriptor);

  Optional<GroupFeature> groupFeature();

  GroupFeature groupFeature(FeatureDescriptor descriptor);

  @Override
  default <T extends RuntimeData> T runtimeData(Feature feature) {
    return runtimeData((FeatureDescriptor) feature);
  }

  default <T extends RuntimeData> T runtimeData(FeatureDescriptor feature) {
    return null;
  }
}
