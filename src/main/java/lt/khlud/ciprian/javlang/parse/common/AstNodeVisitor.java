package lt.khlud.ciprian.javlang.parse.common;

import lt.khlud.ciprian.javlang.parse.AstNode;

import java.util.function.Consumer;

public class AstNodeVisitor {
    public static void visit(AstNode node, Consumer<AstNode> consumer){
        consumer.accept(node);
        for (AstNode child : node.children()) {
            visit(child, consumer);
        }
    }
    public static void visitWithLevel(AstNode node, Consumer<NodeWithLevel> consumer){
        visitWithLevelVisit(new NodeWithLevel(node, 0), consumer);
    }
    private static void visitWithLevelVisit(NodeWithLevel node, Consumer<NodeWithLevel> consumer){
        consumer.accept(node);
        for (AstNode child : node.node().children()) {
            var childNodeWithLevel = new NodeWithLevel(child, node.level() + 1);
            visitWithLevelVisit(childNodeWithLevel, consumer);
        }
    }

    public static void dump(AstNode node){
        visitWithLevel(node, nodeWithLevel -> {
            var sb = new StringBuilder();
            char[] chars = new char[nodeWithLevel.level()];
            for (int i = 0; i < nodeWithLevel.level(); i++) {
                chars[i] = ' ';
            }
            sb.append(new String(chars));
            sb.append(nodeWithLevel.node().token().text());
            sb.append("-");
            sb.append(nodeWithLevel.node().kind());
            System.out.println(sb);
        });
    }
}
