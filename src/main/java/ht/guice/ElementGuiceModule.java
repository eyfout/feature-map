package ht.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.feature.deltastore.DeltaStoreGroupFeature;
import ht.eyfout.map.feature.internal.dictionary.DictionaryFeatureFactory;
import ht.eyfout.map.feature.internal.dictionary.service.DictionaryService;
import ht.eyfout.map.feature.messaging.MessagingGroupFeature;
import ht.eyfout.map.registrar.FeatureRegistrar;
import java.util.function.Supplier;

public class ElementGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ElementMapFactory.class);
    bind(FeatureRegistrar.class);

    Multibinder<FeatureFactory> featureFactoryBinder =
        Multibinder.newSetBinder(binder(), FeatureFactory.class);
    featureFactoryBinder
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DeltaStoreGroupFeature>>() {});
    featureFactoryBinder
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<MessagingGroupFeature>>() {});
    featureFactoryBinder.addBinding().to(DictionaryFeatureFactory.class);
  }

  @Provides
  FeatureFactory<DeltaStoreGroupFeature> deltaStoreFeature() {
    return FeatureFactory.create(DeltaStoreGroupFeature::new);
  }

  @Provides
  FeatureFactory<MessagingGroupFeature> messagingFeature() {
    return FeatureFactory.create(MessagingGroupFeature::new);
  }

  @Provides
  Supplier<DictionaryService> dictionaryService() {
    return DictionaryService::new;
  }
}
