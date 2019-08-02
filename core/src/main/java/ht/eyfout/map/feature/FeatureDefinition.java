package ht.eyfout.map.feature;

import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;

public interface FeatureDefinition {
  default <T extends FeatureOperation> T operations(RuntimeContext context) {
    throw new UnsupportedOperationException();
  }

  FeatureProfile profile();

  default Object init(RuntimeContext context) {
    throw new UnsupportedOperationException();
  }
}
