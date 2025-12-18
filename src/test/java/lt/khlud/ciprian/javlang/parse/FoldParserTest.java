package lt.khlud.ciprian.javlang.parse;

import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.Scanner;
import lt.khlud.ciprian.javlang.parse.common.AstNodeVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoldParserTest {

    @Test
    void testIsTokenSkippable() {
        var simpleFunction = "int f(int a, int b) { return a + b; }";
        var ast = parseCodeToAst(simpleFunction);
        assertEquals(4, ast.children().size());
        AstNodeVisitor.dump(ast);

        // Add your test logic here
    }

    private static AstNode parseCodeToAst(String simpleFunction) {
        var scanner = new Scanner();
        scanner.setCode(StrView.toView(simpleFunction));
        var foldParser = new FoldParser();
        var ast = foldParser.parse(scanner);

        assertTrue(ast.isOk());
        assertNotNull(ast.value());
        return ast.value();
    }
}