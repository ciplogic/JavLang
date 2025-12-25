using SimLang.Common;

namespace SimLang.Lexing;

record struct Token(U8Str Content, int StartPos, TokenType Type)
{
    public static Token Empty => new Token(U8Str.Empty, 0, TokenType.None);
}