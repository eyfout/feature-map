package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataMartFactory
import ht.eyfout.map.data.storage.ScalarMart
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart
import ht.eyfout.map.data.storage.array.IndexGroupDataMart
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification

class IndexGroupSpec extends Specification {

    ArrayGroupDataMart arrStore
    DataMartFactory dsFactory
    ElementMapFactory elementFactory

    def setup() {
        dsFactory = GuiceInstance.get(DataMartFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataMart.class).build()
    }

    def 'Access data store by index'() {
        String expectedValue = 'value'
        given: 'an Array based Group element'
        Group groupElement = elementFactory.group(arrStore)
        when:
        "put a $expectedValue on the Group element"
        groupElement.putScalarValue('key', expectedValue)
        and: 'decorate array store with index data store'
        IndexGroupDataMart indxStore = dsFactory.
        <IndexGroupDataMart, IndexGroupDataMart.IndexGroupDataMartBuilder> create(IndexGroupDataMart.class)
                .array(arrStore)
        then:
        "value at position 0 = $expectedValue"
        expectedValue == indxStore.<ScalarMart> get(0).get()
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
        IndexGroupDataMart indxStore = dsFactory.
        <IndexGroupDataMart, IndexGroupDataMart.IndexGroupDataMartBuilder> create(IndexGroupDataMart.class)
                .array(arrStore)
        then:
        "value at position $expectedValue"
        expectedValue == indxStore.<ScalarMart> get(indxStore.keys().index('key')).get()
        and: ''
        expectedValue == indxStore.<ScalarMart> get('key').get()
    }

}
