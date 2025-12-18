package lt.khlud.ciprian.javlang.parse.semantic;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScanner;
import lt.khlud.ciprian.javlang.lex.common.Token;

import java.util.ArrayList;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;

public class EnumDefinition extends NamedDefinition {
    public ArrayList<String> values = new ArrayList<>();

    public EnumDefinition(String name) {
        super(name);
    }

    public static Res<EnumDefinition> parseFromTokens(ArrayList<Token> tokens) {
        var scanner = new ArrayScanner();
        var tokensView = toView(tokens);
        scanner.fromTokens(tokensView);
        var enumName = scanner.advance().value().text();

        var result = new EnumDefinition(enumName) ;

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
