package lt.khlud.ciprian.javlang.lex;

import lt.khlud.ciprian.javlang.lex.common.Token;

public class NotInterestingTokensUtilities {
    public static boolean isNotInteresting(Token token) {
        var kind = token.kind();
        return switch (kind) {
            case Space, NewLine, Comment -> true;
            default -> false;
        };
    }
}
