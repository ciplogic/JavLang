package lt.khlud.ciprian.javlang.common;

import java.util.List;

public class ListViewUtilities {
    public static <T>  ListView<T> toView(List<T> str) {
        return new ListView<T>(str, 0, str.size());
    }

}
