package lt.khlud.ciprian.javlang.parse.semantic;

import lt.khlud.ciprian.javlang.parse.semantic.common.NamedDefinition;
import lt.khlud.ciprian.javlang.parse.semantic.declarations.EnumDefinition;
import lt.khlud.ciprian.javlang.parse.semantic.declarations.InterfaceDefinition;

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
