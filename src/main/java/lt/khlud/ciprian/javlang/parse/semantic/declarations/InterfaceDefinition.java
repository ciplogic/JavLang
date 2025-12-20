package lt.khlud.ciprian.javlang.parse.semantic.declarations;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.semantic.common.NamedDefinition;

import java.util.ArrayList;

public class InterfaceDefinition extends NamedDefinition {

    public ArrayList<MethodDefinition> Methods = new ArrayList<>();
    public InterfaceDefinition(String name, ArrayList<String> accessesList) {
        super(name, accessesList);
    }

    public static Res<InterfaceDefinition> parseFromTokens(ArrayList<Token> tokens, ArrayList<String> accessorsReader) {
        var arrayScanner = ArrayScannerUtils.scannerOfTokens(tokens);
        var faceToken = arrayScanner.advance();
        var interfaceName = faceToken.value().text();
        if (!arrayScanner.advanceIf("{").value()) {
            return Res.err("curly after interface name.");
        }
        InterfaceDefinition result = new InterfaceDefinition(interfaceName, accessorsReader);
        while (true) {
            Res<ArrayList<Token>> tokensMethod = arrayScanner.readUntil(";");
            if (tokensMethod.isErr()) {
                return tokensMethod.errOf();
            }
            if (tokensMethod.value().size() == 1) {
                return Res.ok(result);
            }
            var method = parseInterfaceMethodMethod(result, tokensMethod.value());
            if (method.isErr()) {
                return method.errOf();
            }
            result.addMethod(method.value());
        }
    }

    private void addMethod(MethodDefinition methodDefinition) {

    }

    private static Res<MethodDefinition> parseInterfaceMethodMethod(InterfaceDefinition result, ArrayList<Token> methodTokens) {
        Res<MethodDefinition> methodDef = MethodDefinition.parseFromTokens(methodTokens);
        return methodDef;
    }
}

