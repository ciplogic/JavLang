namespace SimLang.Common;

public static class Extensions
{
    public static T LastOf<T>(this T[] items)
        => items[items.Length - 1];
}