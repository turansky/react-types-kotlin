// Automatically generated - do not modify!

package typescript

sealed external interface LabeledStatement : Statement, Union.LabeledStatement {
    override val kind: SyntaxKind.LabeledStatement
    val label: Identifier
    val statement: Statement
}
