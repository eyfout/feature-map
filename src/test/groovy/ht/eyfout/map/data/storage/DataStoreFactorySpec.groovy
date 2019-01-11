package ht.eyfout.map.data.storage

import ht.eyfout.map.data.storage.database.query.QueryGroupDataStore
import ht.eyfout.map.data.storage.database.query.QueryGroupDataStoreBuilder
import ht.eyfout.map.data.storage.map.MapGroupDataStore
import ht.eyfout.map.data.storage.map.MapGroupDataStoreBuilder
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DataStoreFactorySpec extends Specification {
    DataStoreFactory factory = GuiceInstance.get(DataStoreFactory.class);

    def "create #builderClass.getSimpleName() for data store #storeClass.getSimpleName()"() {
        expect:
        builderClass.isAssignableFrom(factory.create(storeClass).getClass())
        where:
        storeClass                || builderClass
        MapGroupDataStore.class   || MapGroupDataStoreBuilder.class
        QueryGroupDataStore.class || QueryGroupDataStoreBuilder.class
    }
}
