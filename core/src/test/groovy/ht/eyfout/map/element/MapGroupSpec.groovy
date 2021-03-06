package ht.eyfout.map.element

import ht.eyfout.map.data.storage.DataStorageBuilderFactory
import ht.eyfout.map.data.storage.map.MapGroupDataStorage
import ht.eyfout.map.factory.ElementMapFactory
import ht.eyfout.map.feature.Feature
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class MapGroupSpec extends Specification {

    static ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class)
    Group groupElement

    def setup() {
        groupElement = getFactory().group(
                GuiceInstance.get(DataStorageBuilderFactory.class)
                        .create(MapGroupDataStorage.MapGroupDataStorageBuilder.class).build())
    }

    def 'Can retrieve previously stored value from Group element'() {
        String key = 'key'
        String expectedValue = 'Ayiti'
        given: 'a Group element'
        Group groupElement = getFactory().group()
        when:
        "put( $key, $expectedValue)"
        groupElement.putScalarValue(key, expectedValue)
        then:
        "value is $expectedValue"
        groupElement.getScalarValue(key) == expectedValue
    }

    def '#feature.name() is not present on a featureless Group element'() {
        expect:
        !groupElement.hasFeature(feature)
        where:
        feature << Feature.values()
    }

    def "Add #feature has feature present"() {
        given: 'a Group element'
        when:
        "add $feature"
        groupElement.addFeature(feature)
        then:
        "Group element has $feature"
        groupElement.hasFeature(feature)
        where:
        feature << Feature.values()
    }
}
