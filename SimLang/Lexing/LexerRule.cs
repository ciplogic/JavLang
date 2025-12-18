using SimLang.Lexing;

internal record LexerRule(
    TokenType Type,
    Func<ArraySegment<byte>, int> Match);