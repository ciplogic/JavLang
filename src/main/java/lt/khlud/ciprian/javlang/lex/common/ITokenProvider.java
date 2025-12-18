package lt.khlud.ciprian.javlang.lex.common;

import lt.khlud.ciprian.javlang.common.Res;

import java.util.ArrayList;
import java.util.function.Predicate;

public interface ITokenProvider {
    Res<Token> advance();
    Res<Token> currentToken();

    Res<Boolean> advanceIf(String tokenText);

    Res<ArrayList<Token>> readUntil(String tokenText);
    Res<ArrayList<Token>> readUntilAny(ArrayList<String> tokenText);
    Res<ArrayList<Token>> readUntilFunc(Predicate<String> tokenText);
}
