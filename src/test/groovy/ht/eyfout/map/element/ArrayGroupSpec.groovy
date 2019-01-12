package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataMartFactory
import ht.eyfout.map.data.storage.GroupDataMart
import ht.eyfout.map.data.storage.ScalarMart
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart
import ht.eyfout.map.data.storage.array.IndexGroupDataMart
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification

class ArrayGroupSpec extends Specification {

    ArrayGroupDataMart arrStore
    DataMartFactory dsFactory
    ElementMapFactory elementFactory

    def setup() {
        dsFactory = GuiceInstance.get(DataMartFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataMart.class).build()
    }

    def 'copy'(){
        String expectedValue = 'value#2'
        GroupDataMart mart = dsFactory.<ArrayGroupDataMart,
                ArrayGroupDataMart.ArrayGroupDataMartBuilder>create(ArrayGroupDataMart.class).build()
        Group groupElement = elementFactory.group(mart)
        groupElement.putScalarValue('key#0', 'value#0')
        groupElement.putScalarValue('key#1', 'value#1')
        groupElement.putScalarValue('key#2', expectedValue)

        ArrayGroupDataMart martCopy = mart.copy()
        Group groupElementCopy = elementFactory.group(  martCopy )
        groupElementCopy.putScalarValue('key#4', 'value#4')
        IndexGroupDataMart indexMart = dsFactory.<IndexGroupDataMart,
                IndexGroupDataMart.IndexGroupDataMartBuilder> create(IndexGroupDataMart.class).array( martCopy)
        expect:''
        indexMart.<ScalarMart<String>>get(2).get() == expectedValue

    }
}
