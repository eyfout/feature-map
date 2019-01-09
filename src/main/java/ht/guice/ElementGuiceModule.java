package ht.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.MultibinderBinding;
import ht.eyfout.map.data.storage.DataStore.DataStoreBuilder;
import ht.eyfout.map.data.storage.DataStoreFactory;
import ht.eyfout.map.data.storage.map.MapDataStoreBuilder;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.factory.FeatureElementMapFactory;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.deltastore.DeltaStoreGroupFeature;
import ht.eyfout.map.feature.forward.ForwardScalarFeature;
import ht.eyfout.map.feature.internal.dictionary.DictionaryGroupFeature;
import ht.eyfout.map.feature.internal.dictionary.service.DictionaryService;
import ht.eyfout.map.feature.messaging.MessagingGroupFeature;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import java.util.function.Supplier;

public class ElementGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(ElementMapFactory.class).asEagerSingleton();
    bind(FeatureElementMapFactory.class).asEagerSingleton();
    bind(FeatureRegistrar.class).asEagerSingleton();
    bind(DataStoreFactory.class).to(ht.eyfout.map.data.storage.internal.DataStoreFactory.class).asEagerSingleton();

    Multibinder<FeatureFactory> featureFactoryBinder =
        Multibinder.newSetBinder(binder(), FeatureFactory.class);
    featureFactoryBinder
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DeltaStoreGroupFeature, ForwardScalarFeature>>() {});
    featureFactoryBinder
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<MessagingGroupFeature, ForwardScalarFeature>>() {});
    featureFactoryBinder.addBinding()
        .to(new TypeLiteral<FeatureFactory<DictionaryGroupFeature, ForwardScalarFeature>>() {});

    Multibinder<DataStoreBuilder> builders =
        Multibinder.newSetBinder(binder(), DataStoreBuilder.class);

    builders.addBinding().to(MapDataStoreBuilder.class);
  }


  @Provides
  FeatureFactory<DeltaStoreGroupFeature, ForwardScalarFeature> deltaStoreFeature(FeatureElementMapFactory factory){
    return FeatureFactory.create( (pgFeature)->new DeltaStoreGroupFeature(factory, pgFeature));
  }

  @Provides
  FeatureFactory<MessagingGroupFeature, ForwardScalarFeature> messagingFeature() {
    return FeatureFactory.create(MessagingGroupFeature::new);
  }

  @Provides
  FeatureFactory<DictionaryGroupFeature, ForwardScalarFeature> dictionaryFeature(Supplier<DictionaryService> service){
    return FeatureFactory.create((pgFeature)->new DictionaryGroupFeature(pgFeature, service));
  }

  @Provides
  @Singleton
  Supplier<DictionaryService> dictionaryService() {
    return DictionaryService::new;
  }
}
