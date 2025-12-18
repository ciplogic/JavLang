package lt.khlud.ciprian.javlang.parse.semantic;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.lex.common.TokenType;

import java.util.ArrayList;

public class AccessorsReader {
    public Res<ArrayList<String>> readAccessors(ITokenProvider tokenProvider) {
        ArrayList<String> result = new ArrayList<String>();
        do {
            Res<Token> token = tokenProvider.currentToken();
            if (token.isErr()) return token.errOf();
            if (isAccessor(token.value())) {
                result.add(tokenProvider.advance().value().text());

                continue;
            }
            return Res.ok(result);

        } while (true);
    }

    boolean isAccessor(Token token) {

        if (token.kind() != TokenType.Reserved) {
            return false;
        }
        return switch (token.text()) {
            case "public", "static", "final", "protected", "abstract", "private" -> true;
            default -> false;
        };
    }
}
