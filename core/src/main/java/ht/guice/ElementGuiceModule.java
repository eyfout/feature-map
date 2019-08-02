package ht.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.multibindings.MultibinderBinding;
import ht.eyfout.map.data.storage.DataStorage.DataStorageBuilder;
import ht.eyfout.map.data.storage.DataStorageBuilderFactory;
import ht.eyfout.map.data.storage.GroupDataStorage;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage;
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage.ArrayGroupDataStorageBuilder;
import ht.eyfout.map.data.storage.db.sql.QueryGroupDataStorage;
import ht.eyfout.map.data.storage.db.sql.internal.QueryGroupDataStorageBuilder;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStorage;
import ht.eyfout.map.data.storage.deltastore.DeltaStoreGroupDataStorage.DeltaStoreGroupDataStorageBuilder;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage;
import ht.eyfout.map.data.storage.map.MapGroupDataStorage.MapGroupDataStorageBuilder;
import ht.eyfout.map.factory.ElementMapFactory;
import ht.eyfout.map.factory.FeatureElementMapFactory;
import ht.eyfout.map.factory.FeatureFactory;
import ht.eyfout.map.feature.ScalarFeature;
import ht.eyfout.map.feature.deltastore.DeltaStoreGroupFeature;
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
    bind(DataStorageBuilderFactory.class)
        .to(ht.eyfout.map.data.storage.internal.DataStorageBuilderFactory.class)
        .asEagerSingleton();

    Multibinder<FeatureFactory> featureFactories =
        Multibinder.newSetBinder(binder(), FeatureFactory.class);
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DeltaStoreGroupFeature, ScalarFeature>>() {});
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<MessagingGroupFeature, ScalarFeature>>() {});
    featureFactories
        .addBinding()
        .to(new TypeLiteral<FeatureFactory<DictionaryGroupFeature, ScalarFeature>>() {});

    MapBinder dsBuilders = MapBinder.newMapBinder(binder(), Class.class, DataStorageBuilder.class);

    dsBuilders.addBinding(MapGroupDataStorageBuilder.class).to(MapGroupDataStorageBuilder.class);
    dsBuilders.addBinding(QueryGroupDataStorage.QueryGroupDataStorageBuilder.class).to(QueryGroupDataStorageBuilder.class);
    dsBuilders.addBinding(ArrayGroupDataStorageBuilder.class).to(ArrayGroupDataStorageBuilder.class);
    dsBuilders.addBinding(DeltaStoreGroupDataStorageBuilder.class).to(DeltaStoreGroupDataStorageBuilder.class);
  }





  @Provides
  FeatureFactory<DeltaStoreGroupFeature, ScalarFeature> deltaStore(
      FeatureElementMapFactory factory, DataStorageBuilderFactory dsFactory) {
    return FeatureFactory.create(
        (pgFeature) -> new DeltaStoreGroupFeature(factory, dsFactory, pgFeature));
  }

  @Provides
  FeatureFactory<MessagingGroupFeature, ScalarFeature> messaging() {
    return FeatureFactory.create(MessagingGroupFeature::new);
  }

  @Provides
  FeatureFactory<DictionaryGroupFeature, ScalarFeature> dictionary(
      Supplier<DictionaryService> service) {
    return FeatureFactory.create((pgFeature) -> new DictionaryGroupFeature(pgFeature, service));
  }

  @Provides
  @Singleton
  Supplier<DictionaryService> dictionaryService() {
    return DictionaryService::new;
  }
}
