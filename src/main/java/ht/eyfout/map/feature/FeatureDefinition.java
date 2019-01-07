package ht.eyfout.map.feature;

import ht.eyfout.map.feature.runtime.RuntimeContext;
import ht.eyfout.map.feature.FeatureDescriptor.FeatureState;
import ht.eyfout.map.feature.runtime.data.RuntimeData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface FeatureDefinition {
  default <T extends RuntimeData> T runtimeData(RuntimeContext context){
    throw new UnsupportedOperationException();
  }

  FeatureProfile profile();
  default Object init(RuntimeContext context) {
    if( FeatureState.STATEFUL == profile().descriptor().state() ){
      throw new NotImplementedException();
    } else {
    throw new UnsupportedOperationException();
    }
  }
}
