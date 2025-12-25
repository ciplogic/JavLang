using SimLang.Lexing;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.HeaderRules;

internal class UsingAstRule() : PrefixRule("using")
{
    protected override int MatchRemainder(ArraySegment<Token> tokens, CompilationUnit compilationUnit,
        Permissions permissions)
    {
        if (tokens.Count < 2)
        {
            return 0;
        }
        bool isStatic = false;
        if (tokens[0].Content == "static")
        {
            isStatic = true;
            tokens = tokens.Slice(1);
        }

        if (tokens[1].Content != ";")
        {
            return 0;
        }

        compilationUnit.AddUsing(tokens[0].Content, isStatic);

        return isStatic ? 3 : 2;
    }
}