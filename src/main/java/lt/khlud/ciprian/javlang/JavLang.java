/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package lt.khlud.ciprian.javlang;

import lt.khlud.ciprian.javlang.common.FileUtils;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.common.StrView;
import lt.khlud.ciprian.javlang.lex.ArrayScanner;
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
        var files = FileUtils.filesInDir("src", ".java");
        var filesMap = new HashMap<String, Res<CompilationUnit>>();
        for (var file : files) {
            System.out.println(file);
            var content = FileUtils.readFile(file);
            var scanner = new Scanner();
            StrView view = StrView.toView(content.value());
            scanner.setCode(view);
            var tokens = new ArrayScanner();
            tokens.from(scanner);

            SemanticAstParser semanticParser = new SemanticAstParser();
            var parsedTree = semanticParser.parse(file, tokens);

            filesMap.put(file, parsedTree);

        }
    }
}
