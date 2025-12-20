package lt.khlud.ciprian.javlang.parse.semantic.common;

import java.util.ArrayList;

public class NamedDefinition {
    public NamedDefinition(String name, ArrayList<String> accessesList) {
        this.name = name;
        this.accessesList = accessesList;
    }

    public String name;
    private final ArrayList<String> accessesList;
}
