package lt.khlud.ciprian.javlang.parse.syntactic;

import lt.khlud.ciprian.javlang.parse.syntactic.common.NamedDefinition;
import lt.khlud.ciprian.javlang.parse.syntactic.declarations.EnumDefinition;
import lt.khlud.ciprian.javlang.parse.syntactic.declarations.InterfaceDefinition;

import java.util.ArrayList;
import lt.khlud.ciprian.javlang.parse.syntactic.declarations.RecordDefinition;

public class CompilationUnit {

    public String packageName;
    public ArrayList<String> imports = new ArrayList<>();
    public ArrayList<NamedDefinition> definitions = new ArrayList<>();

    public void addImport(String importName) {
        imports.add(importName);
    }

    void addDefinition(NamedDefinition namedDefinition) {
        definitions.add(namedDefinition);
    }

    @Override
    public String toString() {
        if (!definitions.isEmpty()){
            return definitions.get(0).toString();
        }
        return super.toString();
    }
}
