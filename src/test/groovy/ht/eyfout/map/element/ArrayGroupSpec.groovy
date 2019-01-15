package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataStorageBuilderFactory
import ht.eyfout.map.data.storage.GroupDataStorage
import ht.eyfout.map.data.storage.ScalarDataStorage
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance

class ArrayGroupSpec extends MapGroupSpec {

    ArrayGroupDataStorage arrStore
    DataStorageBuilderFactory dsFactory
    ElementMapFactory elementFactory
    static int MAX_SEED = 100

    def setup() {
        dsFactory = GuiceInstance.get(DataStorageBuilderFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataStorage.class).build()
        groupElement = elementFactory.group()

    }

    def 'copy'() {
        Integer expectedValue = 88
        String expectedString = 'Inspiron'
        GroupDataStorage storage = dsFactory.<ArrayGroupDataStorage,
                ArrayGroupDataStorage.ArrayGroupDataStorageBuilder> create(ArrayGroupDataStorage.class).build()
        Group groupElement = elementFactory.group(storage)
        seed(groupElement)

        ArrayGroupDataStorage storageCopy = storage.copy()
        Group groupElementCopy = elementFactory.group(storageCopy)
        groupElementCopy.putScalarValue('key#4', expectedString)

        GroupDataStorage indexstorage = dsFactory.<ArrayGroupDataStorage,
                ArrayGroupDataStorage.ArrayGroupDataStorageBuilder> create(ArrayGroupDataStorage.class).index(storageCopy)
        expect: ''
        indexstorage.<ScalarDataStorage<Integer>> get(expectedValue).get() == expectedValue
        and: ''
        indexstorage.<ScalarDataStorage<String>> get(4).get() == expectedString
    }

    def 'set value on scalar'(){
        Integer expectedValue = 88
        GroupDataStorage storage = dsFactory.<ArrayGroupDataStorage,
                ArrayGroupDataStorage.ArrayGroupDataStorageBuilder> create(ArrayGroupDataStorage.class).build()
        Group groupElement = elementFactory.group(storage)
        seed(groupElement)

        ArrayGroupDataStorage storageCopy = storage.copy()
        Group groupElementCopy = elementFactory.group(storageCopy)

        Scalar<Integer> scalar = groupElement.getScalar("key#4")
        Scalar<Integer> scalarOfCopy = groupElementCopy.getScalar("key#4")
        scalarOfCopy.set(expectedValue)

        expect:
        scalarOfCopy.get() == expectedValue
        scalar.get() != expectedValue


    }

    def seed(Group group) {
        for (int n = 0; n <= MAX_SEED; n++) {
            group.<Integer> putScalarValue("key#$n", n)
        }
    }
}
