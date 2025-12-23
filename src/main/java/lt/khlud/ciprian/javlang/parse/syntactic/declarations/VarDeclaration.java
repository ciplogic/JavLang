package lt.khlud.ciprian.javlang.parse.syntactic.declarations;

import lt.khlud.ciprian.javlang.parse.syntactic.common.NamedDefinition;

import java.util.ArrayList;

public class VarDeclaration extends NamedDefinition {
    private final TypeDeclaration _typeDeclaration;

    public VarDeclaration(String name, TypeDeclaration typeDeclaration) {
        super(name, new ArrayList<>());
        this._typeDeclaration = typeDeclaration;
    }
}
