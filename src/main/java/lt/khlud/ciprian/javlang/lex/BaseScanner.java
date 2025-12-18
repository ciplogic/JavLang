package lt.khlud.ciprian.javlang.lex;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class BaseScanner implements ITokenProvider {

    public Res<Boolean> advanceIf(String tokenText) {
        var currentToken = currentToken();
        if (!currentToken.isOk()) {
            return currentToken.errOf();
        }

        if (tokenText.equals(currentToken.value().text())) {
            advance();
            return Res.ok(true);
        }

        return Res.ok(false);
    }

    public Res<ArrayList<Token>> readUntil(String tokenText) {
        return readUntilFunc(anObject -> tokenText.equals(anObject));
    }
    public Res<ArrayList<Token>> readUntilAny(ArrayList<String> tokenText) {
        return readUntilFunc(tokenText::contains);
    }

    public Res<ArrayList<Token>> readUntilFunc(Predicate<String> tokenText){
        var result = new ArrayList<Token>();
        while (true) {
            var token = currentToken();
            if (!token.isOk()) return token.errOf();
            result.add(token.value());
            if (token.value().text().isEmpty()) break;
            advance();
            if (tokenText.test(token.value().text())) break;
        }
        return Res.ok(result);
    }

}
