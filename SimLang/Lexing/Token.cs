using SimLang.Lexing;

record struct Token(byte[] Utf8Value, int StartPos, TokenType Type);