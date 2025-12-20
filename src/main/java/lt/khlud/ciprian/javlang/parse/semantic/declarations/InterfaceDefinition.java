package lt.khlud.ciprian.javlang.parse.semantic.declarations;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.semantic.common.NamedDefinition;

import java.util.ArrayList;

public class InterfaceDefinition extends NamedDefinition {

    public InterfaceDefinition(String name, ArrayList<String> accessesList) {
        super(name, accessesList);
    }

    public static Res<InterfaceDefinition> parseFromTokens(ArrayList<Token> value, ArrayList<String> accessorsReader) {
        var interfaceName = value.get(0).text();
        InterfaceDefinition result = new InterfaceDefinition(interfaceName, accessorsReader);
        return Res.ok(result);
    }
}
