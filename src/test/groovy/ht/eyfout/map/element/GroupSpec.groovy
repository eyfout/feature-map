package ht.eyfout.map.element

import ht.eyfout.map.factory.ElementMapFactory
import ht.eyfout.map.feature.Feature
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class GroupSpec extends Specification{

    ElementMapFactory factory = GuiceInstance.get(ElementMapFactory.class)

    def 'Can retrieve previously stored value from Group element'() {
        String key = 'key'
        String expectedValue = 'Ayiti'
        given: 'a Group element'
        Group element = getFactory().group()
        when:"put( $key, $expectedValue)"
        element.putScalarValue(key, expectedValue)
        then: "value is $expectedValue"
        element.getScalarValue(key) == expectedValue
    }

    def 'Featureless Group elements have no feature(s)'(){
        Group element = getFactory().group()
        expect:
        !element.hasFeature(feature)
        where:
        feature << Feature.values()
    }

    def "Add #feature has feature present"(){
        given: 'a Group element'
        Group element = getFactory().group()
        when: "add $feature"
        element.addFeature(feature)
        then: "Group element has $feature"
        element.hasFeature(feature)
        where:
        feature << Feature.values()
    }
}
