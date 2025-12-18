package lt.khlud.ciprian.javlang.parse;

import lt.khlud.ciprian.javlang.lex.common.Token;

import java.util.ArrayList;

public record AstNode(AstNodeKind kind, ArrayList<AstNode> children, Token token) {
    public static AstNode nonTerminal(AstNodeKind astNodeKind) {
        return new AstNode(astNodeKind, new ArrayList<>(), Token.Empty);
    }

    public static AstNode terminal(Token token) {
        return new AstNode(AstNodeKind.Terminal, new ArrayList<>(), token);
    }

    public void addTerminal(Token token) {
        children.add(terminal(token));
    }

    public void foldChildren(int pos, AstNodeKind astNodeKind) {
        var foldNode = AstNode.nonTerminal(astNodeKind);
        foldNode.children().addAll(children.subList(pos, children.size()));
        int countFolded = foldNode.children().size();
        for (int i = 0; i < countFolded; i++) {
            children.removeLast();
        }
        children.add(pos, foldNode);
    }

    public String toString() {
        if (children.isEmpty()) return token.text();
        return children.stream().map(Object::toString).reduce("", (a, b) -> a + b);
    }

    public AstNode get(int index) {
        return children.get(index);
    }
}
