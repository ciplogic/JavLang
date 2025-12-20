package lt.khlud.ciprian.javlang.lex;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.semantic.common.AccessorsReader;

import java.util.ArrayList;
import java.util.List;

import static lt.khlud.ciprian.javlang.lex.NotInterestingTokensUtilities.isNotInteresting;

public class ArrayScannerUtils {
    public static ArrayScanner scannerOfTokens(ArrayList<Token> tokensView) {
        ArrayScanner scanner = new ArrayScanner();
        scanner.TokensArray = getTokensArray(tokensView);
        return scanner;
    }

    public static Res<ArrayList<String>> getAccessors(ITokenProvider tokenProvider){
        AccessorsReader accessorsReader = new AccessorsReader();
        return accessorsReader.readAccessors(tokenProvider);
    }
    public static Token[] getTokensArray(List<Token> Tokens) {
        var result = new Token[Tokens.size()];
        for (int i = 0; i < Tokens.size(); i++) {
            result[i] = Tokens.get(i);
        }
        return result;
    }

    public static ArrayScanner from(Scanner scanner) {
        List<Token> Tokens = new ArrayList<>();
        while (true) {
            Res<Token> token = scanner.advance();
            if (!token.isOk()) {
                break;
            }
            if (token.value().text().isEmpty()) {
                break;
            }
            if (isNotInteresting(token.value())) {
                continue;
            }
            Tokens.add(token.value());
        }
        ArrayScanner result = new ArrayScanner();
        result.TokensArray = ArrayScannerUtils.getTokensArray(Tokens);
        return result;
    }
}
