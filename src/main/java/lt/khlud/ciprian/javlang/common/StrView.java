package lt.khlud.ciprian.javlang.common;

public record StrView(String str, int start, int length) {
    public static StrView toView(String str) {
        return new StrView(str, 0, str.length());
    }

    public StrView slice(int start, int length) {
        return new StrView(str, this.start + start, length);
    }

    public StrView slice(int start) {
        return new StrView(str, this.start + start, length - start);
    }

    public String toString() {
        return str.substring(start, start + length);
    }

    public char get(int index) {
        return str.charAt(start + index);
    }

    public boolean startsWith(String otherText) {
        if (otherText.length() > length) return false;
        for (int i = 0; i < otherText.length(); i++) {
            if (get(i) != otherText.charAt(i)) return false;
        }
        return true;
    }
}
