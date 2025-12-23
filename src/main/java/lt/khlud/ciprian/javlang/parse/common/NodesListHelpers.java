package lt.khlud.ciprian.javlang.parse.common;

import lt.khlud.ciprian.javlang.common.ListView;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.AstNode;

import java.util.List;

public final class NodesListHelpers{
    public static AstNode[] getAstNodesOfView(ListView<AstNode> firstSection) {
        AstNode[] firstSectionList = new AstNode[firstSection.length()];
        firstSection.toArray(firstSectionList);
        return firstSectionList;
    }

    public static int indexOf(List<AstNode> terminals, String text) {
        for (int i = 0; i < terminals.size(); i++) {
            AstNode astNode = terminals.get(i);
            if (astNode.token().text().equals(text)) return i;
        }
        return -1;
    }

    public static int lastIndexOfOpenParen(List<AstNode> terminals, String openParenToken) {
        for (int i = terminals.size() - 1; i >= 0; i--) {
            AstNode astNode = terminals.get(i);
            if (astNode.token().text().equals(openParenToken)) return i;
        }
        return -1;
    }

    public static final List<String> closeParenTokens = List.of(")", "]", "}");
    public static final List<String> openParenTokens = List.of("(", "[", "{");

    public static int indexOfCloseParen(Token token) {
        for (int i = 0; i < closeParenTokens.size(); i++) {
            String closeParenToken = closeParenTokens.get(i);
            if (token.text().equals(closeParenToken)) return i;
        }
        return -1;
    }
}
