package ht.eyfout.dsl.annotation.processor

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class ElementDSL implements  ASTTransformation{
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        if(null != astNodes){
            astNodes.each {
            }
        }
    }
}
