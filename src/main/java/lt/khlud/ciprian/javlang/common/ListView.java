package lt.khlud.ciprian.javlang.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;


public record ListView<T>(List<T> items, int start, int length) {
    public ListView(List<T> items) {
        this(items, 0, items.size());
    }

    public ListView<T> slice(int start, int length) {
        return new ListView<>(items, this.start + start, length);
    }

    public ListView<T> slice(int start) {
        return slice(this.start + start, length - start);
    }

    public ListView<T> trimEnd(int count) {
        return slice(this.start, length - count);
    }

    public int size() {
        return length;
    }

    public void forEach(Consumer<T> consumer) {
        items.subList(start, start + length).forEach(consumer);
    }

    public ArrayList<T> toList() {
        return new ArrayList<>(items.subList(start, start + length));
    }

    public void populate(List<T> target) {
        target.clear();
        target.addAll(items.subList(start, start + length));
    }

    public String toString() {
        if (length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            sb.append(items.get(i));
        }
        return sb.toString();
    }

    public void toArray(T[] array) {
        for (int i = 0; i < length; i++) {
            array[i] = items.get(start + i);
        }
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public String join(String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(items.get(start + i));
            if (i < length - 1) sb.append(separator);
        }
        return sb.toString();
    }

    public <TOut> ListView<TOut> mapTo(Function<T, TOut> mapper) {
        var result = new ArrayList<TOut>(length);
        for (int i = 0; i < length; i++) {
            result.add(mapper.apply(get(i)));
        }
        return toView(result);
    }

    public T get(int i) {
        return items.get(start + i);
    }
}
