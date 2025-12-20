package lt.khlud.ciprian.javlang.parse.semantic.declarations;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.semantic.common.NamedDefinition;

import java.util.ArrayList;

import static lt.khlud.ciprian.javlang.lex.ArrayScannerUtils.getAccessors;

public class MethodDefinition extends NamedDefinition {

    private ArrayList<String> Variables = new ArrayList<>();

    public MethodDefinition(String name, ArrayList<String> accessesList) {
        super(name, accessesList);
    }

    public static Res<MethodDefinition> parseFromTokens(ArrayList<Token> methodTokens) {
        var scanner = ArrayScannerUtils.scannerOfTokens(methodTokens);

        Res<ArrayList<String>> accessors = getAccessors(scanner);
        if (accessors.isErr()) {
            return accessors.errOf();
        }
        var readUntilOpenParen = scanner.readUntil("(");
        var methodAndReturnTypeTokens = readUntilOpenParen.value();
        var methodName = methodAndReturnTypeTokens.get(readUntilOpenParen.value().size() - 2).text();

        var methodDeclaration = new MethodDefinition(methodName, accessors.value());
        var separators = new ArrayList<String>();
        separators.add(",");
        separators.add(")");
        while (true) {
            var parenTokens = scanner.readUntilAny(separators).value();
            if (parenTokens.size() < 3) {
                return Res.ok(methodDeclaration);
            }
            methodDeclaration.addVariable(parenTokens.get(parenTokens.size() - 2).text());
        }
    }

    private void addVariable(String varName) {
        Variables.add(varName);
    }
}
