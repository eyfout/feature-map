package ht.eyfout.map.feature.internal.dictionary;

import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.internal.dictionary.service.DictionaryService;
import java.util.function.Supplier;
import javax.inject.Inject;

public class DictionaryFeatureFactory extends FeatureFactory<DictionaryGroupFeature> {
  @Inject
  public DictionaryFeatureFactory(Supplier<DictionaryService> service) {
    super((pgFeature) -> new DictionaryGroupFeature(pgFeature, service));
  }
}
