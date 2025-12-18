package lt.khlud.ciprian.javlang.parse.semantic;

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
}
