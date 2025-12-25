using SimLang.Lexing;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.HeaderRules;

internal class NamespaceAstRule() : PrefixRule("namespace")
{

    protected override int MatchRemainder(ArraySegment<Token> tokens, CompilationUnit compilationUnit,
        Permissions permissions)
    {
        if (tokens.Count < 3)
        {
            return 0;
        }

        if (tokens[1].Content != ";")
        {
            return 0;
        }

        compilationUnit.Namespace = tokens[0].ToString();

        return 2;
    }
}