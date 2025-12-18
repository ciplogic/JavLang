package lt.khlud.ciprian.javlang.lex;

import lt.khlud.ciprian.javlang.common.ListView;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;

import java.util.ArrayList;
import java.util.List;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;
import static lt.khlud.ciprian.javlang.lex.NotInterestingTokensUtilities.isNotInteresting;

public class ArrayScanner extends BaseScanner implements ITokenProvider {
    public Token[] TokensArray = new Token[0];
    int position = 0;

    @Override
    public Res<Token> advance() {
        var currentToken = currentToken();
        if (currentToken.isOk()) {
            position++;
            return currentToken;
        }
        return currentToken.errOf();
    }

    @Override
    public String toString() {
        var listTokens = new ArrayList<Token>();
        var lenRead = 100;
        if (lenRead + position > TokensArray.length) lenRead = TokensArray.length - position;
        for (int i = position; i < position + lenRead; i++) {
            listTokens.add(TokensArray[i]);
        }
        var view = toView(listTokens);
        return view.mapTo(Token::text).join(" ");
    }

    @Override
    public Res<Token> currentToken() {
        if (position < TokensArray.length) {
            return Res.ok(TokensArray[position]);
        }
        return Res.ok(Token.Empty);
    }

    public void from(Scanner scanner) {
        List<Token> Tokens = new ArrayList<>();
        while (true) {
            var token = scanner.advance();
            if (!token.isOk()) {
                return;
            }
            if (token.value().text().isEmpty()) {
                break;
            }
            if (isNotInteresting(token.value())) {
                continue;
            }
            Tokens.add(token.value());
        }
        TokensArray = getTokensArray(Tokens);
    }

    private static Token[] getTokensArray(List<Token> Tokens) {
        var result = new Token[Tokens.size()];
        for (int i = 0; i < Tokens.size(); i++) {
            result[i] = Tokens.get(i);
        }
        return result;
    }

    public void fromTokens(ListView<Token> tokensView) {
        var tokens = tokensView.toList();
        TokensArray = getTokensArray(tokens);
    }
}

