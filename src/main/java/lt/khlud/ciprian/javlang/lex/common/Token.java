package lt.khlud.ciprian.javlang.lex.common;

public record Token(TokenType kind, String text) {
    public static final Token Empty = new Token(TokenType.None, "");
}

