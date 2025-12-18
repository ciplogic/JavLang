package lt.khlud.ciprian.javlang.lex.common;

import lt.khlud.ciprian.javlang.common.StrView;

import java.util.List;

public class LexUtils {
    public static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t';
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    public static int matchAll(StrView text, IMatchChar matchChar) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.get(i);
            if (!matchChar.matches(c)) {
                return i;
            }
        }
        return text.length();
    }

    public static int matchRulesLength(StrView text, IMatchChar firstMatch, IMatchChar matchChar) {
        if (!firstMatch.matches(text.get(0))) return 0;
        for (int i = 1; i < text.length(); i++) {
            char c = text.get(i);
            if (!matchChar.matches(c)) {
                return i;
            }
        }
        return text.length();
    }

    public static int startsWithAny(StrView strView, List<String> parenTokens) {
        for(String token : parenTokens) {
            if(strView.startsWith(token)) {
                return token.length();
            }
        }
        return 0;
    }

    public static boolean isEoln(char c) {
        return c == '\n' || c == '\r';
    }
}
