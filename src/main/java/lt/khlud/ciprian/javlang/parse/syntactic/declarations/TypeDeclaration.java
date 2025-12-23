package lt.khlud.ciprian.javlang.parse.syntactic.declarations;

import lt.khlud.ciprian.javlang.common.ListView;
import lt.khlud.ciprian.javlang.lex.common.Token;

import java.util.ArrayList;

public class TypeDeclaration {

    private final ArrayList<String> typeInfo;

    public TypeDeclaration(ListView<Token> tokenListView) {
        this.typeInfo = tokenListView.mapTo(Token::text).toList();
    }
}
