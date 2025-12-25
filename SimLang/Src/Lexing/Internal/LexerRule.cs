namespace SimLang.Lexing.Internal;

internal record LexerRule(
    TokenType Type,
    Func<ArraySegment<byte>, int> Match);