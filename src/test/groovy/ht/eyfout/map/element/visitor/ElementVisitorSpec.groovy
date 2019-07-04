package ht.eyfout.map.element.visitor

import ht.eyfout.map.data.storage.DataStorageBuilderFactory
import ht.eyfout.map.data.storage.array.ArrayGroupDataStorage
import ht.eyfout.map.visitor.VisitorResult
import ht.eyfout.map.element.Group
import ht.eyfout.map.element.Scalar
import ht.eyfout.map.factory.ElementMapFactory
import ht.guice.GuiceInstance
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ElementVisitorSpec extends Specification {
    class SysStreamVisitor implements ElementVisitor {
        Map<String, Integer> result

        SysStreamVisitor() {
        }

        @Override
        void pre(Group element) {
            result = [:]
        }

        @Override
        void post(Group element) {
            println("Finish")
        }

        @Override
        VisitorResult visit(String name, Scalar element) {
            println "visited : $name"
            if (name =~ "type") {
                return VisitorResult.TERMINATE
            }
            result.put(name, element.get())
            return VisitorResult.CONTINUE
        }

        @Override
        def <T> T result() {
            return result;
        }
    }

    Group groupElement
    ElementVisitor visitor

    def setup() {
        groupElement = GuiceInstance
                .get(ElementMapFactory.class)
                .group(GuiceInstance
                .get(DataStorageBuilderFactory.class)
                .create(ArrayGroupDataStorage.ArrayGroupDataStorageBuilder.class));
        visitor = new SysStreamVisitor();
    }

    def 'visit page with #upperBound scalars'() {
        (1..upperBound).each {
            groupElement.putScalarValue("scalar#$it", it)
        }
        expect:
        groupElement.<Map> accept(visitor).size() == upperBound
        where:
        upperBound << [1, 13, 31]
    }

    def 'Halt processing of when you find first scalar named type'() {

        (1..upperBound).each {
            groupElement.putScalarValue("scalar#$it", it)
            groupElement.putScalarValue("type#$it", it)
        }

        expect:
        groupElement.<Map> accept(visitor).size() <= upperBound
        where:
        upperBound << [1, 13, 31]
    }
}
