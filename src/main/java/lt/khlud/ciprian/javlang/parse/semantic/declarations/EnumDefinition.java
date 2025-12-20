package lt.khlud.ciprian.javlang.parse.semantic.declarations;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.semantic.common.NamedDefinition;

import java.util.ArrayList;

public class EnumDefinition extends NamedDefinition {
    public ArrayList<String> values = new ArrayList<>();

    public EnumDefinition(String name, ArrayList<String> accessorsReader) {
        super(name, accessorsReader);
    }

    public static Res<EnumDefinition> parseFromTokens(ArrayList<Token> tokens, ArrayList<String> accessorsReader) {
        var scanner = ArrayScannerUtils.scannerOfTokens(tokens);
        var enumName = scanner.advance().value().text();

        var result = new EnumDefinition(enumName, accessorsReader);

        if (!scanner.advanceIf("{").value()) {
            return Res.err("Expected '{' after enum name");
        }
        while (!scanner.advanceIf("}").value()) {
            result.values.add(scanner.advance().value().text());
            scanner.advanceIf(",");
        }

        return Res.ok(result);
    }
}
