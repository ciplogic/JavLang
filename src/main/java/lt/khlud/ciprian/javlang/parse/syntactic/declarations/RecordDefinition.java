package lt.khlud.ciprian.javlang.parse.syntactic.declarations;

import java.util.ArrayList;
import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.ArrayScannerUtils;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.parse.syntactic.common.NamedDefinition;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;

public class RecordDefinition extends NamedDefinition {

    public RecordDefinition(String name, ArrayList<String> accessesList) {
        super(name, accessesList);
    }
    
    public ArrayList<VarDeclaration> mainProperties = new ArrayList<>();
    
    public void addProperty(VarDeclaration foundProperty) {
        mainProperties.add(foundProperty);
    }
    public static Res<RecordDefinition> parseFromTokens(ArrayList<Token> tokens, ArrayList<String> accessorsReader) {
        var scanner = ArrayScannerUtils.scannerOfTokens(tokens);
        var recordName = scanner.advance().value().text();
        scanner.advanceIf("(");
        RecordDefinition result =  new RecordDefinition(recordName, accessorsReader);

        var readUntilOpenParen = scanner.readUntil("(");
        var methodAndReturnTypeTokens = toView(readUntilOpenParen.value());
        return Res.ok(result);
    }

}
