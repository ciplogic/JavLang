package lt.khlud.ciprian.javlang.lex.rules;

import lt.khlud.ciprian.javlang.lex.common.IMatchRule;
import lt.khlud.ciprian.javlang.lex.common.TokenType;

public record LexerRule(IMatchRule matchRule, TokenType tokenType) {

}
