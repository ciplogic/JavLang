package lt.khlud.ciprian.javlang.common;

public record Res<T>(T value, String errMessage) {

    public static <T> Res<T> ok(T value) {
        return new Res<>(value, null);
    }

    public static <T> Res<T> err(String message) {
        return new Res<>(null, message);
    }

    public <TResult> Res<TResult> errOf() {
        return new Res<>(null, this.errMessage());
    }

    public boolean isOk() {
        return errMessage == null;
    }

    public boolean isErr() {
        return !isOk();
    }
}

