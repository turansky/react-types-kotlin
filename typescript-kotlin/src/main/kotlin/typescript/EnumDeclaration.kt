// Automatically generated - do not modify!

package typescript

sealed external interface EnumDeclaration : DeclarationStatement, JSDocContainer, Union.EnumDeclaration {
    override val kind: SyntaxKind.EnumDeclaration
    override val name: Identifier
    val members: NodeArray<EnumMember>
}
