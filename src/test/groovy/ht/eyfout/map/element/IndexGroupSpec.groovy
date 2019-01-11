package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataStoreFactory
import ht.eyfout.map.data.storage.ScalarStore
import ht.eyfout.map.data.storage.array.ArrayGroupDataStore
import ht.eyfout.map.data.storage.array.IndexGroupDataStore
import ht.eyfout.map.data.storage.array.IndexGroupDataStoreBuilder
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification

class IndexGroupSpec extends Specification {

    ArrayGroupDataStore arrStore
    DataStoreFactory dsFactory
    ElementMapFactory elementFactory

    def setup() {
        dsFactory = GuiceInstance.get(DataStoreFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataStore.class).build()
    }

    def 'Access data store by index'() {
        String expectedValue = 'value'
        given: 'an Array based Group element'
        Group groupElement = elementFactory.group(arrStore)
        when:
        "put a $expectedValue on the Group element"
        groupElement.putScalarValue('key', expectedValue)
        and: 'decorate array store with index data store'
        IndexGroupDataStore indxStore = dsFactory.
        <IndexGroupDataStore, IndexGroupDataStoreBuilder> create(IndexGroupDataStore.class)
                .array(arrStore)
        then:
        "value at position 0 = $expectedValue"
        expectedValue == indxStore.<ScalarStore> get(0).get()
    }

    def 'Access data store using index key map'() {
        String expectedValue = 'value'
        given: 'an Array based Group element'
        Group groupElement = elementFactory.group(arrStore)
        when:
        "put a $expectedValue on the Group element"
        groupElement.putScalarValue('NaN', 'NaN')
        groupElement.putScalarValue('key', expectedValue)
        and: 'decorate array store with index data store'
        IndexGroupDataStore indxStore = dsFactory.
        <IndexGroupDataStore, IndexGroupDataStoreBuilder> create(IndexGroupDataStore.class)
                .array(arrStore)
        then:
        "value at position $expectedValue"
        expectedValue == indxStore.<ScalarStore> get(indxStore.keys().index('key')).get()
        and: ''
        expectedValue == indxStore.<ScalarStore> get('key').get()
    }

}
