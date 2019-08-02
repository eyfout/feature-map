package ht.eyfout.dsl

import ht.eyfout.struct.data.Element
import ht.eyfout.struct.data.MapElement

class ElementCategory {
    static Object asType(Map map, Class elem) {
        if (elem == Element) {
            def result = new MapElement()
            map.each { key, value ->
                if (value instanceof Map) {
                    result.putElement(key, value as Element)
                } else {
                    result.put(key, value)
                }
            }
            return result
        } else {
            return map.asType(elem)
        }
    }
}
