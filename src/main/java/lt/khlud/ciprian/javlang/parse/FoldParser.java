package lt.khlud.ciprian.javlang.parse;

import lt.khlud.ciprian.javlang.common.ListView;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.lex.common.TokenType;
import lt.khlud.ciprian.javlang.parse.common.AstNodeVisitor;
import lt.khlud.ciprian.javlang.parse.common.NodesListHelpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lt.khlud.ciprian.javlang.parse.common.NodesListHelpers.*;

public class FoldParser {
    public Res<AstNode> parse(ITokenProvider scanner) {
        AstNode result = AstNode.nonTerminal(AstNodeKind.Program);
        while (true) {
            var resToken = scanner.advance();
            if (!resToken.isOk()) return resToken.errOf();
            if (resToken.value().text().isEmpty()) break;
            var token = resToken.value();
            if (isTokenSkippable(token)) continue;

            result.addTerminal(token);
            handleFoldingBasedOnToken(result, token);
        }

        foldStatements(result, List.of(";", ":", ","));
        return Res.ok(result);
    }

    private void foldStatements(AstNode result, List<String> splitTokens) {
        for(var token : splitTokens) {
            AstNodeVisitor.visit(result, node -> {
                splitByStatementToken(node, token);
            });
        }
    }

    private void splitByStatementToken(AstNode node, String token) {
        int splitPos = NodesListHelpers.indexOf(node.children(), token);
        if (splitPos == -1) return;
        var nodeView = new ListView<AstNode>(node.children());
        var leftView = nodeView.slice(0, splitPos);
        AstNode leftNode = buildStatementFromView(leftView);
        var rightView = nodeView.slice(splitPos + 1);
        AstNode rightNode = buildStatementFromView(rightView);
        var finalList = new ArrayList<AstNode>();
        if (leftNode != null)
        {
            finalList.add(leftNode);
        }
        finalList.add(node.get(splitPos));

        if (rightNode != null) {
            finalList.add(rightNode);
        }
        node.children().clear();
        node.children().addAll(finalList);
    }

    private static AstNode buildStatementFromView(ListView<AstNode> nodesList) {
        if (nodesList.isEmpty()) {
            return null;
        }
        return new AstNode(AstNodeKind.Statement, nodesList.toList(), Token.Empty);
    }

    static boolean isTokenSkippable(Token token) {
        TokenType tokenType = token.kind();
        return tokenType.equals(TokenType.Space) || tokenType.equals(TokenType.NewLine) || tokenType.equals(TokenType.Comment);
    }

    private static void handleFoldingBasedOnToken(AstNode result, Token token) {
        if (token.kind().equals(TokenType.Parenthesis)) {
            int index = NodesListHelpers.indexOfCloseParen(token);
            if (index != -1) {
                foldParen(result, index);
            }
        }
    }

    private static void foldParen(AstNode parentNode, int index) {
        String openParenToken = openParenTokens.get(index);
        List<AstNode> parentChildren = parentNode.children();
        int lastOpenParenIndex = lastIndexOfOpenParen(parentChildren, openParenToken);
        if (lastOpenParenIndex == -1) return;

        ListView<AstNode> nodeListView = new ListView<>(parentChildren);
        var firstSectionList = getAstNodesOfView(nodeListView.slice(0, lastOpenParenIndex));
        var remainder = nodeListView.slice(lastOpenParenIndex);
        var parenthesisNode = AstNode.nonTerminal(AstNodeKind.Parenthesis);
        remainder.populate(parenthesisNode.children());
        parentChildren.clear();
        Collections.addAll(parentChildren, firstSectionList);
        parentChildren.add(parenthesisNode);

        splitParenthesisNode(parenthesisNode);
    }

    private static void splitParenthesisNode(AstNode parenthesisNode) {
        var middleView = new ListView<>(parenthesisNode.children());
        var middleSection = middleView.slice(1, middleView.length() - 2);
        var nodes = getAstNodesOfView(middleSection);
        var firstNode = parenthesisNode.children().getFirst();
        var lastNode = parenthesisNode.children().getLast();

        var parenContentNode = AstNode.nonTerminal(AstNodeKind.BlockBody);
        Collections.addAll(parenContentNode.children(), nodes);

        var parenthesisNodeChildren = parenthesisNode.children();
        parenthesisNodeChildren.clear();
        parenthesisNodeChildren.add(firstNode);
        parenthesisNodeChildren.add(parenContentNode);
        parenthesisNodeChildren.add(lastNode);
    }

}


