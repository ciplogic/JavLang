package lt.khlud.ciprian.javlang.lex.rules;

import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.common.LexUtils;
import lt.khlud.ciprian.javlang.lex.common.TokenType;

import java.util.List;

public class LexerRules {
    public static List<LexerRule> rules = getDefaultRules();

    private static List<LexerRule> getDefaultRules() {
        return List.of(
                new LexerRule(LexerRules::matchSpaces, TokenType.Space),
                new LexerRule(LexerRules::matchEoln, TokenType.NewLine),
                new LexerRule(LexerRules::matchComment, TokenType.Comment),
                new LexerRule(LexerRules::matchString, TokenType.String),
                new LexerRule(LexerRules::matchSeparators, TokenType.Separator),
                new LexerRule(LexerRules::matchAnnotation, TokenType.Annotation),
                new LexerRule(LexerRules::matchNumber, TokenType.Number),
                new LexerRule(LexerRules::matchReserved, TokenType.Reserved),
                new LexerRule(LexerRules::matchId, TokenType.Identifier),
                new LexerRule(LexerRules::matchParen, TokenType.Parenthesis),
                new LexerRule(LexerRules::matchOperators, TokenType.Operator)
        );
    }

    private static final List<String> reservedWords = List.of(
            "public",
            "static",
            "final", "enum",
            "abstract",
            "protected",
            "private",
            "record",
            "class", "extends", "implements", "interface", "new", "super", "this");

    private static int matchReserved(StrView strView) {
        int matchIdLen = matchId(strView);
        if (matchIdLen == 0) return 0;
        int matchLength = LexUtils.startsWithAny(strView, reservedWords);

        return matchLength != matchIdLen ? 0 : matchLength;
    }

    private static int matchString(StrView strView) {
        char firstChar = strView.get(0);
        boolean isDoubleQuote = firstChar == '"' || firstChar == '\'';
        if (!isDoubleQuote) return 0;
        boolean isEscaped = false;
        for (int i = 1; i < strView.length(); i++) {
            char currentChar = strView.get(i);
            if (isEscaped) {
                isEscaped = false;
                continue;
            }
            if (currentChar == '\\') {
                isEscaped = true;
                continue;
            }
            if (currentChar == firstChar) return i + 1;
        }
        return 0;
    }

    private static int matchLineComment(StrView strView) {
        if (!strView.startsWith("//")) {
            return 0;
        }
        for (int i = 2; i < strView.length(); i++) {
            if (strView.get(i) == '\n') return i;
        }
        return strView.length();
    }

    private static int matchMultiLineComment(StrView strView) {
        if (!strView.startsWith("/*")) {
            return 0;
        }
        for (int i = 2; i < strView.length() - 1; i++) {
            if (strView.get(i) == '*' && strView.get(i + 1) == '/') return i + 2;
        }
        return strView.length();
    }


    private static int matchComment(StrView strView) {
        var lineCommentLength = matchLineComment(strView);
        if (lineCommentLength != 0) return lineCommentLength;
        return matchMultiLineComment(strView);
    }

    private static int matchNumber(StrView strView) {
        return LexUtils.matchAll(strView, LexUtils::isDigit);
    }

    private static int matchAnnotation(StrView strView) {
        if (strView.get(0) != '@') {
            return 0;
        }
        int matchLength = matchId(strView.slice(1));
        return matchLength == 0 ? 0 : matchLength + 1;
    }

    private static int matchEoln(StrView strView) {
        return LexUtils.matchAll(strView, LexUtils::isEoln);
    }

    static List<String> operators = List.of("+", "-", "*", "/", "%", "==",
            "!=", ">", "<", ">=", "<=", "||", "&&", "!",
            "="
    );

    private static int matchOperators(StrView strView) {
        return LexUtils.startsWithAny(strView, operators);
    }

    static List<String> separators = List.of(".", ",", ";", "?", ":");

    private static int matchSeparators(StrView strView) {
        return LexUtils.startsWithAny(strView, separators);
    }

    public static List<String> parenTokens = List.of("(", ")", "[", "]", "{", "}");

    private static int matchParen(StrView strView) {
        return LexUtils.startsWithAny(strView, parenTokens);
    }

    private static int matchSpaces(StrView view) {
        return LexUtils.matchAll(view, LexUtils::isWhitespace);
    }

    private static int matchId(StrView view) {
        return LexUtils.matchRulesLength(view, LexUtils::isLetter,
                c -> LexUtils.isLetter(c) || LexUtils.isDigit(c));
    }

}

