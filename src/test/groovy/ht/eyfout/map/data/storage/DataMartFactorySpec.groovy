package ht.eyfout.map.data.storage

import ht.eyfout.map.data.storage.db.sql.QueryGroupDataMart
import ht.eyfout.map.data.storage.map.MapGroupDataMart
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DataMartFactorySpec extends Specification {
    DataMartFactory factory = GuiceInstance.get(DataMartFactory.class);

    def "create #builderClass.getSimpleName() for data store #storeClass.getSimpleName()"() {
        expect:
        builderClass.isAssignableFrom(factory.create(storeClass).getClass())
        where:
        storeClass               || builderClass
        MapGroupDataMart.class   || MapGroupDataMart.MapGroupDataStoreBuilder.class
        QueryGroupDataMart.class || QueryGroupDataMart.QueryGroupDataStoreBuilder.class
    }
}
