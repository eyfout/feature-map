package ht.eyfout.map.features;

import ht.eyfout.map.feature.Feature;
import ht.eyfout.map.feature.FeatureDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class FeatureProfile {
  private static Map<FeatureDescriptor, FeatureProfile> pool = new HashMap();
  FeatureDescriptor featureDescriptor;
  private int order;

  protected FeatureProfile(FeatureDescriptor descriptor) {
    this.featureDescriptor = descriptor;

    for(FeatureDescriptor featureDescriptor : descriptor.dependsOn()){
      order = order | featureDescriptor.rank();
    }
    order = order | descriptor.rank();
  }

  public static FeatureProfile create(Feature feature) {
    return create((FeatureDescriptor) feature);
  }

  public static FeatureProfile create(FeatureDescriptor rank) {
    FeatureProfile result = pool.get(rank);
    if (Objects.isNull(result)){
      result = new FeatureProfile(rank);
      pool.put(rank, result);
    }
    return result;
  }

  @Override
  public String toString() {
    return "name: " + descriptor().name() + ", order: " + order();
  }

  public FeatureDescriptor descriptor(){
    return featureDescriptor;
  }

  public int order(){
    return order;
  }
}
