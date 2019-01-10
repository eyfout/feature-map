package ht.eyfout.map.element.internal;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDefinition;
import ht.eyfout.map.feature.FeatureDescriptor;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
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
  default <T extends FeatureOperation> T operations(Feature feature) {
    return operations((FeatureDescriptor) feature);
  }

  default <T extends FeatureOperation> T operations(FeatureDescriptor feature) {
    return null;
  }

  @Override
  default boolean hasFeature(Feature feature) {
    return hasFeature((FeatureDescriptor) feature);
  }

  boolean hasFeature(FeatureDescriptor feature);
}
