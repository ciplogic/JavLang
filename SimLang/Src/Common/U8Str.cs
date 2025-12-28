using System.Text;

namespace SimLang.Common;

public readonly struct U8Str(byte[] text)
{
    public byte[] Text { get; } = text;

    public override string ToString()
        => Encoding.UTF8.GetString(Text);

    public static U8Str Empty { get; } = new U8Str([]);

    public override bool Equals(object? otherObj)
    {
        if (otherObj is not U8Str other)
        {
            return false;
        }
        Span<byte> span1 = Text.AsSpan();
        Span<byte> span2 = other.Text.AsSpan();
        return span1.SequenceEqual(span2);
    }


    public static bool operator ==(U8Str content, string other)
    {
        if (content.Text.Length != other.Length)
        {
            return false;
        }

        return content.ToString() == other;
    }

    public static bool operator !=(U8Str content, string other) 
        => !(content == other);

    public static implicit operator U8Str(string text)
        => new(Encoding.UTF8.GetBytes(text));
}