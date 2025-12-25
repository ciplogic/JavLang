using SimLang.Lexing;

namespace SimLang.SyntaxParser.Common;

interface ITokenRuleMatcher
{
    int MatchOperation(ArraySegment<Token> tokens, CompilationUnit compilationUnit, Permissions permissions);
}