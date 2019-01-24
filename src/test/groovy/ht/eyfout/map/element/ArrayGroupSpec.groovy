package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataStorageBuilderFactory
import ht.eyfout.map.data.storage.GroupDataStorage
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage
import ht.eyfout.map.factory.ElementMapFactory
import ht.eyfout.map.scalar.ScalarReference
import ht.guice.GuiceInstance

class ArrayGroupSpec extends MapGroupSpec {

    ArrayGroupDataStorage arrStore
    DataStorageBuilderFactory dsFactory
    ElementMapFactory elementFactory
    static int MAX_SEED = 100

    def setup() {
        dsFactory = GuiceInstance.get(DataStorageBuilderFactory.class)
        elementFactory = GuiceInstance.get(ElementMapFactory.class)
        arrStore = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).build()
        groupElement = elementFactory.group()

    }

    def 'copy'() {
        Integer expectedValue = 88
        String expectedString = 'Inspiron'
        GroupDataStorage storage = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).build()
        Group groupElement = elementFactory.group(storage)
        seed(groupElement)

        ArrayGroupDataStorage storageCopy = storage.copy()
        Group groupElementCopy = elementFactory.group(storageCopy)
        groupElementCopy.putScalarValue('key#4', expectedString)

        GroupDataStorage indexstorage = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).index(storageCopy)
        expect: ''
        indexstorage.<Integer> get(expectedValue) == expectedValue
        and: ''
        indexstorage.<String> get(4) == expectedString
    }

    def 'set value on scalar'() {
        Integer expectedValue = 88
        GroupDataStorage storage = dsFactory.create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class).build()
        Group groupElement = elementFactory.group(storage)
        seed(groupElement)

        ArrayGroupDataStorage storageCopy = storage.copy()
        Group groupElementCopy = elementFactory.group(storageCopy)

        Scalar<Integer> scalar = ScalarReference.getScalar('key#4', groupElement)
        Scalar<Integer> scalarOfCopy = ScalarReference.getScalar('key#4', groupElementCopy)
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
