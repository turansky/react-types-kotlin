// Automatically generated - do not modify!

package typescript

external sealed interface CaseClause : Node, JSDocContainer, Union.CaseClause_ {
override val kind: SyntaxKind.CaseClause
override val parent: CaseBlock
val expression: Expression
val statements: NodeArray<Statement>
}
