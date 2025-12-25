namespace SimLang.Lexing;

public enum TokenType
{
    None,
    Space,
    Operator,
    Number,
    Identifier,
    Comment,
    Eoln,
    Quote,
    Template,
    Reserved,
    Brace
}