package ht.eyfout.struct.data

import spock.lang.Specification

class ArrayElementSpec extends Specification {
    Element<Integer> intElement;

    def setup() {
        intElement = new ArrayElement(new ElementKeyResolver(['pyValue': 1, 'pyName': 2, 'pyLastElement':12_200]))
        0..12_200.each {
            intElement.put(it, "String_$it")
        }
    }

    def "set value on element"() {
        when:
        intElement.put(2, "String")
        then:
        "String" == intElement.get(2)
    }

    def "copies are identical"() {
        when:
        Element<Integer> copy = intElement.copy()
        then:
        copy.get(1) == intElement.get(1)
    }

    def "copies are not the same"() {
        given: 'a copy'
        Element<Integer> copy = intElement.copy()

        when:
        copy.put(2, 'JDoe')

        then:
        copy.get(2) == 'JDoe'
        intElement.get(2) != copy.get(2)
    }

    def "get values by name"(){
        expect:
        intElement.get(1) == intElement.get( intElement.keysFunction().apply('pyValue'))
    }
}
