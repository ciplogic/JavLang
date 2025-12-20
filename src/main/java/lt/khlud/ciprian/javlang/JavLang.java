/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package lt.khlud.ciprian.javlang;

import lt.khlud.ciprian.javlang.common.FileUtils;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.ArrayScanner;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.Scanner;
import lt.khlud.ciprian.javlang.parse.semantic.CompilationUnit;
import lt.khlud.ciprian.javlang.parse.semantic.SemanticAstParser;

import java.util.HashMap;

/**
 *
 * @author cipri
 */
public class JavLang {
    public static void main(String[] args) {
        parseAll();
        for (var i = 0; i < 10; i++) {
            var start = System.currentTimeMillis();
            parseAll();
            var end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start) + " ms.");
        }
    }

    private static void parseAll() {
        var files = FileUtils.filesInDir("src/main", ".java");
        var filesMap = new HashMap<String, Res<CompilationUnit>>();
        for (var file : files) {
            var content = FileUtils.readFile(file);
            var scanner = new Scanner();
            StrView view = StrView.toView(content.value());
            scanner.setCode(view);
            ArrayScanner arrayScanner = ArrayScannerUtils.from(scanner);

            SemanticAstParser semanticParser = new SemanticAstParser();
            var parsedTree = semanticParser.parse(file, arrayScanner);

            filesMap.put(file, parsedTree);
        }
    }
}
