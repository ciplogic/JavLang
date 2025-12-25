using System.Text;

namespace SimLang.Common;

public record struct U8Str(byte[] Text)
{
    public override string ToString() 
        => Encoding.UTF8.GetString(Text);

    public static U8Str Empty { get; } = new U8Str([]);
}