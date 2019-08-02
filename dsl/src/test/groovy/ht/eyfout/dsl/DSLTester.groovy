package ht.eyfout.dsl

import ht.eyfout.dsl.annotation.processor.ElementDSL
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.tools.ast.TransformTestHelper

class DSLTester extends GroovyTestCase {
    public void testDSL(){
        def file = new File("${System.getProperty("user.dir")}\\src\\test\\groovy\\ht\\eyfout\\dsl\\DSLSpecification.groovy")
        assert file.exists()
        def invoker = new TransformTestHelper(new ElementDSL(), CompilePhase.CANONICALIZATION)
        def clazz = invoker.parse(file)
        def tester = clazz.newInstance()
        tester.main(null)
    }
}
