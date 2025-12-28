using SimLang.Lexing;
using SimLang.SyntaxParser.Common;
using SimLang.SyntaxParser.HeaderRules;

namespace SimLang.SyntaxParser;

public class SimpleAstParser
{
    private readonly ITokenRuleMatcher[] _defaultHeaderRules = BuildRules();
    private readonly ITokenRuleMatcher[] _declarationRules = BuildDeclarationRules();
    public ArraySegment<Token> _tokensBuffer;
    private Permissions _permissions = new([]);

    public override string ToString()
    {
        return string.Join(" ", _tokensBuffer.Select(token => token.Content));
    }

    private static ITokenRuleMatcher[] BuildRules()
    {
        List<ITokenRuleMatcher> rules =
        [
            new UsingAstRule(),
            new NamespaceAstRule()
        ];
        return rules.ToArray();
    }

    private static ITokenRuleMatcher[] BuildDeclarationRules()
    {
        List<ITokenRuleMatcher> rules =
        [
            new EnumRule(),
            new InterfaceRule(),
        ];
        return rules.ToArray();
    }

    internal CompilationUnit Parse(string filePath, Token[] tokens)
    {
        CompilationUnit result = new CompilationUnit(filePath);
        _tokensBuffer = new ArraySegment<Token>(tokens);

        ParseHeader(result);
        _permissions = PermissionsReader.ReadPermissions(_tokensBuffer);

        Advance(_permissions.Words.Length);

        ParseDeclarations(result);

        return result;
    }

    private void ParseDeclarations(CompilationUnit result)
    {
        bool found;
        do
        {
            found = false;
            var rules = _declarationRules;
            foreach (var rule in rules)
            {
                var matchLen = rule.MatchOperation(_tokensBuffer, result, _permissions);
                if (matchLen > 0)
                {
                    found = true;
                    Advance(matchLen);
                    break;
                }
            }
        } while (found);
    }

    private void ParseHeader(CompilationUnit result)
    {
        bool found;
        do
        {
            found = false;
            var rules = _defaultHeaderRules;
            foreach (var rule in rules)
            {
                var matchLen = rule.MatchOperation(_tokensBuffer, result, _permissions);
                if (matchLen > 0)
                {
                    found = true;
                    Advance(matchLen);
                    break;
                }
            }
        } while (found);
    }

    private void Advance(int matchLen)
        => _tokensBuffer = _tokensBuffer.Slice(matchLen);
}