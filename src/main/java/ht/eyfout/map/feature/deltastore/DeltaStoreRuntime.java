package ht.eyfout.map.feature.deltastore;

import ht.eyfout.map.data.storage.map.MapGroupDataStore;
import ht.eyfout.map.feature.runtime.data.FeatureOperation;
import java.util.function.Supplier;

public final class DeltaStoreRuntime implements FeatureOperation {
  Supplier<Integer> sizeFunc;
  public DeltaStoreRuntime(MapGroupDataStore store) {
    sizeFunc = ()->store.size();
  }

  public int size(){
    return sizeFunc.get();
  }

}
