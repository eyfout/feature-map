package ht.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;
import ht.eyfout.map.data.storage.DataMart.DataStoreBuilder;
import ht.eyfout.map.data.storage.DataMartFactory;
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart.ArrayGroupDataStoreBuilder;
import ht.eyfout.map.data.storage.array.IndexGroupDataMart.IndexGroupDataStoreBuilder;
import ht.eyfout.map.data.storage.db.sql.internal.QueryGroupDataStoreBuilder;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataMart.DeltaStoreGroupDataStoreBuilder;
import ht.eyfout.map.data.storage.map.MapGroupDataMart.MapGroupDataStoreBuilder;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.factory.FeatureElementMapFactory;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.deltastore.DeltaStoreGroupFeature;
import ht.eyfout.map.feature.forward.ForwardScalarFeature;
import ht.eyfout.map.feature.internal.dictionary.DictionaryGroupFeature;
import ht.eyfout.map.feature.internal.dictionary.service.DictionaryService;
import ht.eyfout.map.feature.messaging.MessagingGroupFeature;
import ht.eyfout.map.registrar.internal.FeatureRegistrar;
import ht.sample.data.source.Database;
import java.util.function.Supplier;

class ElementGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Database.class);

    bind(ElementMapFactory.class).asEagerSingleton();
    bind(FeatureElementMapFactory.class).asEagerSingleton();
    bind(FeatureRegistrar.class).asEagerSingleton();
    bind(DataMartFactory.class)
        .to(ht.eyfout.map.data.storage.internal.DataMartFactory.class)
        .asEagerSingleton();

    Multibinder<FeatureFactory> featureFactories =
        Multibinder.newSetBinder(binder(), FeatureFactory.class);
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DeltaStoreGroupFeature, ForwardScalarFeature>>() {});
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<MessagingGroupFeature, ForwardScalarFeature>>() {});
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DictionaryGroupFeature, ForwardScalarFeature>>() {});

    Multibinder<DataStoreBuilder> dsBuilders =
        Multibinder.newSetBinder(binder(), DataStoreBuilder.class);

    dsBuilders.addBinding().to(MapGroupDataStoreBuilder.class);
    dsBuilders.addBinding().to(QueryGroupDataStoreBuilder.class);
    dsBuilders.addBinding().to(ArrayGroupDataStoreBuilder.class);
    dsBuilders.addBinding().to(IndexGroupDataStoreBuilder.class);
    dsBuilders.addBinding().to(DeltaStoreGroupDataStoreBuilder.class);
  }

  @Provides
  FeatureFactory<DeltaStoreGroupFeature, ForwardScalarFeature> deltaStore(
      FeatureElementMapFactory factory, DataMartFactory dsFactory) {
    return FeatureFactory.create(
        (pgFeature) -> new DeltaStoreGroupFeature(factory, dsFactory, pgFeature));
  }

  @Provides
  FeatureFactory<MessagingGroupFeature, ForwardScalarFeature> messaging() {
    return FeatureFactory.create(MessagingGroupFeature::new);
  }

  @Provides
  FeatureFactory<DictionaryGroupFeature, ForwardScalarFeature> dictionary(
      Supplier<DictionaryService> service) {
    return FeatureFactory.create((pgFeature) -> new DictionaryGroupFeature(pgFeature, service));
  }

  @Provides
  @Singleton
  Supplier<DictionaryService> dictionaryService() {
    return DictionaryService::new;
  }
}
