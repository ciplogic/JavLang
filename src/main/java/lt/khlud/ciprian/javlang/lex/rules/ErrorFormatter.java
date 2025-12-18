package lt.khlud.ciprian.javlang.lex.rules;

import lt.khlud.ciprian.javlang.common.StrView;

public final class ErrorFormatter {
    public static String format(StrView remainderCode) {
        int pos = indexOf(remainderCode, '\n');
        if (pos != -1) {
            remainderCode = remainderCode.slice(0, pos);
        }
        if (remainderCode.length() > 200) {
            remainderCode = remainderCode.slice(0, 200);
        }
        return "" + remainderCode;

    }

    private static int indexOf(StrView view, char c) {
        for (int i = 0; i < view.length(); i++) {
            if (view.get(i) == c) return i;
        }
        return -1;
    }
}
