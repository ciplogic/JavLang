using SimLang.Lexing;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.HeaderRules;

abstract class PrefixRule(string prefix) : ITokenRuleMatcher
{
    private readonly string _prefix = prefix;

    public int MatchOperation(ArraySegment<Token> tokens, CompilationUnit compilationUnit, Permissions permissions)
    {
        if (tokens.Count < 2)
        {
            return 0;
        }

        if (tokens[0].Content != _prefix)
        {
            return 0;
        }

        return 1 + MatchRemainder(tokens.Slice(1), compilationUnit, permissions);
    }

    protected abstract int MatchRemainder(ArraySegment<Token> slice, CompilationUnit compilationUnit,
        Permissions permissions);
}