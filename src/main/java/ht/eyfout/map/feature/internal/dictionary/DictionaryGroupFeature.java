package ht.eyfout.map.feature.internal.dictionary;

import ht.eyfout.map.feature.internal.Feature;
import ht.eyfout.map.feature.internal.dictionary.service.DictionaryService;
import ht.eyfout.map.feature.FeatureProfile;
import ht.eyfout.map.feature.GroupFeature;
import java.util.function.Supplier;

public class DictionaryGroupFeature extends GroupFeature {

  DictionaryService service;

  public DictionaryGroupFeature(GroupFeature groupFeature, Supplier<DictionaryService> service) {
    super(groupFeature);
    this.service = service.get();
  }

  @Override
  public FeatureProfile profile() {
    return FeatureProfile.create(Feature.DICTIONARY);
  }
}
