using SimLang.Lexing;
using SimLang.SyntaxParser.Common;
using SimLang.SyntaxParser.Declarations;

namespace SimLang.SyntaxParser.HeaderRules;

class InterfaceRule() : PrefixRule("interface")
{
    protected override int MatchRemainder(ArraySegment<Token> slice, CompilationUnit compilationUnit,
        Permissions permissions)
    {
        var scanner = slice.ToArrayLexer();
        var startLen = scanner.Length;
        var interfaceDeclaration = new InterfaceDeclaration(scanner.Advance().Content);
        interfaceDeclaration.Permissions = permissions;
        var isFullyDefined = scanner.AdvanceIf("{");
        if (isFullyDefined)
        {
            compilationUnit.AddDeclaration(interfaceDeclaration);
        }

        var endLen = scanner.Length;
        return startLen - endLen;
    }
}