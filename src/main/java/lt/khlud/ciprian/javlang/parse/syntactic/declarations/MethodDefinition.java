package lt.khlud.ciprian.javlang.parse.syntactic.declarations;

import lt.khlud.ciprian.javlang.common.ListView;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.syntactic.common.NamedDefinition;

import java.util.ArrayList;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;
import static lt.khlud.ciprian.javlang.lex.ArrayScannerUtils.getAccessors;

public class MethodDefinition extends NamedDefinition {

    public final ArrayList<VarDeclaration> Variables = new ArrayList<>();
    public TypeDeclaration returnType;

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
        var methodAndReturnTypeTokens = toView(readUntilOpenParen.value());
        var methodName = methodAndReturnTypeTokens.get(readUntilOpenParen.value().size() - 2).text();
        var methodDeclaration = new MethodDefinition(methodName, accessors.value());
        methodDeclaration.returnType = new TypeDeclaration(methodAndReturnTypeTokens.trimEnd(2));

        var separators = new ArrayList<String>();
        separators.add(",");
        separators.add(")");
        while (true) {
            var parenTokens = scanner.readUntilAny(separators).value();
            if (parenTokens.size() < 3) {
                return Res.ok(methodDeclaration);
            }
            var paramTokensView = toView(parenTokens);
            methodDeclaration.addVariable(paramTokensView);
        }
    }

    private void addVariable(ListView<Token> varTokens) {

        var variableType = new TypeDeclaration(varTokens.trimEnd(2));
        String varName = varTokens.get(varTokens.size() - 2).text();
        var varDeclaration = new VarDeclaration(varName, variableType);
        Variables.add(varDeclaration);
    }
}

