package ht.eyfout.map.data.storage

import ht.eyfout.map.data.storage.db.sql.QueryGroupDataStorage
import ht.eyfout.map.data.storage.map.MapGroupDataStorage
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DataStorageFactorySpec extends Specification {
    DataStorageBuilderFactory factory = GuiceInstance.get(DataStorageBuilderFactory.class);

    def "create #builderClass.getSimpleName() for data store #storeClass.getSimpleName()"() {
        expect:
        builderClass.isAssignableFrom(factory.create(storeClass).getClass())
        where:
        storeClass                  || builderClass
        MapGroupDataStorage.class   || MapGroupDataStorage.MapGroupDataStorageBuilder.class
        QueryGroupDataStorage.class || QueryGroupDataStorage.QueryGroupDataStorageBuilder.class
    }
}
