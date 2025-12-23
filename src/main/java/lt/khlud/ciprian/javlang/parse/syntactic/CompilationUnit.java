package lt.khlud.ciprian.javlang.parse.syntactic;

import lt.khlud.ciprian.javlang.parse.syntactic.common.NamedDefinition;
import lt.khlud.ciprian.javlang.parse.syntactic.declarations.EnumDefinition;
import lt.khlud.ciprian.javlang.parse.syntactic.declarations.InterfaceDefinition;

import java.util.ArrayList;

public class CompilationUnit {

    public String packageName;
    public ArrayList<String> imports = new ArrayList<>();
    public ArrayList<NamedDefinition> definitions = new ArrayList<>();

    public void addImport(String importName) {
        imports.add(importName);
    }

    public void addEnumDefinition(EnumDefinition enumDefinition) {
        definitions.add(enumDefinition);
    }

    public void addInterfaceDefinition(InterfaceDefinition interfaceDefinition) {
        definitions.add(interfaceDefinition);
    }
}
