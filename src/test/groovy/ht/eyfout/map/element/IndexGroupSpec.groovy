package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataStorageBuilderFactory
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage
import ht.eyfout.map.data.storage.array.IndexGroupDataStorage
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification

class IndexGroupSpec extends Specification {

    ArrayGroupDataStorage arrStore
    DataStorageBuilderFactory dsFactory
    ElementMapFactory elementFactory

    def setup() {
        dsFactory = GuiceInstance.get(DataStorageBuilderFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).build()
    }

    def 'Access data store by index'() {
        String expectedValue = 'value'
        given: 'an Array based Group element'
        Group groupElement = elementFactory.group(arrStore)
        when:
        "put a $expectedValue on the Group element"
        groupElement.putScalarValue('key', expectedValue)
        and: 'decorate array store with index data store'
        IndexGroupDataStorage indxStore = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).index(arrStore)
        then:
        "value at position 0 = $expectedValue"
        expectedValue == indxStore.get(0)
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
        IndexGroupDataStorage indxStore = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).index(arrStore)

        then:
        "value at position $expectedValue"
        expectedValue == indxStore.<String> get(indxStore.keys().index('key'))
        and: ''
        expectedValue == indxStore.<String> get('key')
    }

}
