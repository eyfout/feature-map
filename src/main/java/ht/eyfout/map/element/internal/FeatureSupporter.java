package ht.eyfout.map.element.internal;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.GroupFeature;
import ht.eyfout.map.feature.runtime.data.RuntimeData;
import ht.eyfout.map.registrar.internal.FeatureRegistrar.FeatureBundle;
import java.util.Optional;

public interface FeatureSupporter extends ht.eyfout.map.element.FeatureSupporter {

  FeatureBundle bundle();
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

  Optional<FeatureDefinition> chain();

  FeatureDefinition definition(FeatureDescriptor descriptor);

  @Override
  default <T extends RuntimeData> T runtimeData(Feature feature) {
    return runtimeData((FeatureDescriptor) feature);
  }

  default <T extends RuntimeData> T runtimeData(FeatureDescriptor feature) {
    return null;
  }
}
