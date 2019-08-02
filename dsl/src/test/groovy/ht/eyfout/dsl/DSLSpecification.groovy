package ht.eyfout.dsl

import ht.eyfout.struct.data.Element
import spock.lang.Specification
import spock.util.mop.Use

@Use(ElementCategory)
class DSLSpecification extends Specification {

    def "DSL is applied correctly"() {
        def page = [
                'key':'value',
                'key#1':'value#2',
                'element' : [
                        'criteria' : 'id'
                ]
        ] as Element<String>

        expect:
        page.get('key') == 'value'
        page.getElement('element').get('criteria') == 'id'
    }

    public static void main(String[] args){

    }
}
