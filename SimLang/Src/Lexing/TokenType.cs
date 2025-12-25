namespace SimLang.Lexing;

enum TokenType
{
    None,
    Space,
    Operators,
    Brace,
    Number,
    Identifier,
    Comment,
    Eoln,
    Quote,
    Template
}