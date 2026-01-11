using SimLang.Common;
using SimLang.Lexing;
using SimLang.SyntaxParser.Declarations;

namespace SimLang.SyntaxParser.HeaderRules;

class InterfaceMethodDeclaration(U8Str name) : NamedDeclaration(name)
{
    public static int Match(ArrayLexer scanner, InterfaceDeclaration interfaceDeclaration)
    {
        var tokens = scanner.ReadUntil("(");

        var methodDeclaration = new InterfaceMethodDeclaration(tokens[tokens.Length - 2].Content);

        return 0;
    }
}