using SimLang.Common;
using SimLang.Lexing;

namespace SimLang.SyntaxParser.Common;

public static class PermissionsReader
{
    private static readonly U8Str[] _permissions =
        ["public", "private", "internal", "protected", "readonly", "static", "const"];

    public static Permissions ReadPermissions(ArraySegment<Token> tokens)
    {
        var listResult = new List<U8Str>();
        do
        {
            int indexOf = IndexOfPermission(tokens[0]);
            if (indexOf == -1)
            {
                break;
            }

            listResult.Add(tokens[0].Content);
            tokens = tokens.Slice(1);
        } while (true);

        return new Permissions(listResult.ToArray());
    }

    private static int IndexOfPermission(Token token)
    {
        return Array.IndexOf(_permissions, token.Content.ToString());
    }
}