using SimLang.Lexing;
using SimLang.SyntaxParser.Common;
using SimLang.SyntaxParser.Declarations;

namespace SimLang.SyntaxParser.HeaderRules;

class EnumRule() : PrefixRule("enum")
{
    protected override int MatchRemainder(ArraySegment<Token> slice, CompilationUnit compilationUnit,
        Permissions permissions)
    {
        var lexer = slice.ToArrayLexer();
        var originalLen = lexer.Length;
        var enumName = lexer.Advance().Content;
        lexer.AdvanceIf("{");
        var enumDeclaration = new EnumDeclaration(enumName, permissions);
        compilationUnit.AddDeclaration(enumDeclaration);
        do
        {
            var enumField = lexer.Advance().Content;
            lexer.AdvanceIf(",");
            enumDeclaration.Children.Add(new NamedDeclaration(enumField));

        } while (!lexer.AdvanceIf("}"));
        var finalLen = lexer.Length;
        return originalLen - finalLen;
    }
}