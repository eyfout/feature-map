package ht.eyfout.map.dsl

import ht.eyfout.map.element.Group
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification

class ElementSpec extends Specification{

    def elem(Closure cl){
        def groupSpec = new GroupElementSpec()
        cl.rehydrate(groupSpec, this, this).call()

    }

    class GroupElementSpec {
        Group groupElement = GuiceInstance.get(ElementMapFactory.class).group()
        @Override
        void setProperty(String s, Object o) {
            if(o instanceof Map){

            } else if( o instanceof List){

            } else {
                groupElement.putScalarValue(name, o)
            }
        }
    }
}
