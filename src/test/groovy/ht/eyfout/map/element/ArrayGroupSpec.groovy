package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataMartFactory
import ht.eyfout.map.data.storage.GroupDataMart
import ht.eyfout.map.data.storage.ScalarMart
import ht.eyfout.map.data.storage.array.ArrayGroupDataMart
import ht.eyfout.map.data.storage.array.IndexGroupDataMart
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance

class ArrayGroupSpec extends MapGroupSpec {

    ArrayGroupDataMart arrStore
    DataMartFactory dsFactory
    ElementMapFactory elementFactory
    static int MAX_SEED = 100

    def setup() {
        dsFactory = GuiceInstance.get(DataMartFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataMart.class).build()
        groupElement = elementFactory.group()

    }

    def 'copy'() {
        Integer expectedValue = 88
        String expectedString = 'Inspiron'
        GroupDataMart mart = dsFactory.<ArrayGroupDataMart,
                ArrayGroupDataMart.ArrayGroupDataMartBuilder> create(ArrayGroupDataMart.class).build()
        Group groupElement = elementFactory.group(mart)
        seed(groupElement)

        ArrayGroupDataMart martCopy = mart.copy()
        Group groupElementCopy = elementFactory.group(martCopy)
        groupElementCopy.putScalarValue('key#4', expectedString)

        GroupDataMart indexMart = dsFactory.<ArrayGroupDataMart,
                ArrayGroupDataMart.ArrayGroupDataMartBuilder> create(ArrayGroupDataMart.class).index(martCopy)
        expect: ''
        indexMart.<ScalarMart<Integer>> get(expectedValue).get() == expectedValue
        and: ''
        indexMart.<ScalarMart<String>> get(4).get() == expectedString
    }

    def seed(Group group) {
        for (int n = 0; n <= MAX_SEED; n++) {
            group.<Integer> putScalarValue("key#$n", n)
        }
    }
}
