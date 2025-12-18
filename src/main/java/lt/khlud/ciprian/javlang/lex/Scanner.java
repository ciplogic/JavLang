package lt.khlud.ciprian.javlang.lex;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.lex.rules.ErrorFormatter;
import lt.khlud.ciprian.javlang.lex.rules.LexerRules;

/**
 *
 * @author cipri
 */
public class Scanner extends BaseScanner implements ITokenProvider {
    StrView _view;

    public void setCode(StrView view) {
        _view = view;
    }

    public Res<Token> currentToken() {
        if (_view.length() == 0) return Res.ok(Token.Empty);
        var rules = LexerRules.rules;
        for (var rule : rules) {
            var matchLength = rule.matchRule().matches(_view);
            if (matchLength == 0) {
                continue;
            }
            var spanToken = _view.slice(0, matchLength);
            return Res.ok(new Token(rule.tokenType(), spanToken.toString()));
        }
        return Res.err("Unexpected end of input: '" + ErrorFormatter.format(_view) + "'.");
    }

    public Res<Token> advance() {
        var resToken = currentToken();
        if (resToken.isOk()) {
            _view = _view.slice(resToken.value().text().length());
        }
        return resToken;
    }

    public Res<Boolean> advance(String value) {
        var token = currentToken();
        if (!token.isOk()) return token.errOf();
        boolean result = value.equals(token.value().text());
        if (result) {
            _view = _view.slice(value.length());
        }

        return Res.ok(result);
    }
}

