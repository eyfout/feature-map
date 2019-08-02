package ht.eyfout.struct.data

import spock.lang.Specification

class MapElementSpec extends Specification{

    Element<String> mapElement;

    def setup() {
        mapElement = new MapElement()
        def keys = ['pyValue', 'pyName', 'pyLastElement']
        keys.each {
            mapElement.put(it, "Value for $it")
        }
    }

    def "set value on element"() {
        when:
        mapElement.put('pyName', "String")
        then:
        "String" == mapElement.get('pyName')
    }

    def "copies are identical"() {
        when:
        Element<Integer> copy = mapElement.copy()
        then:
        copy.get('pyValue') == mapElement.get('pyValue')
    }

    def "copies are not the same"() {
        given: 'a copy'
        Element<String> copy = mapElement.copy()

        when:
        copy.put('pyName', 'JDoe')

        then:
        copy.get('pyName') == 'JDoe'
        mapElement.get('pyName') != copy.get('pyName')
    }

    def "get values by name"(){
        expect:
        mapElement.get('pyValue') == mapElement.get( mapElement.keysFunction().apply('pyValue'))
    }
}
