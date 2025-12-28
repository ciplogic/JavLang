using SimLang.Lexing;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.HeaderRules;

class InterfaceRule() : PrefixRule("interface")
{
    protected override int MatchRemainder(ArraySegment<Token> slice, CompilationUnit compilationUnit,
        Permissions permissions)
    {
        return 0;
    }
}