namespace SimLang.Lexing.Internal;

internal static class LexerAllRules
{
    private static string[] Operators =
    [
        ";", ":", "{", "}", "[", "]", "(", ")",

        ",", "=>", "||", "&&", "<", ">",
        "+=", "++", "+", "-",
        "=", "!", ".", ":", "?"
    ];

    public static readonly LexerRule[] Rules = BuildRules();

    private static LexerRule[] BuildRules()
    {
        List<LexerRule> result =
        [
            new(TokenType.Space, MatchSpaces),
            new(TokenType.Eoln, MatchEoln),
            new(TokenType.Comment, MatchComment),
            new(TokenType.Comment, MatchOperator),
            new(TokenType.Identifier, MatchIdentifier),
            new(TokenType.Number, MatchNumber),
            new(TokenType.Quote, MatchQuote),
            new(TokenType.Template, MatchTemplate),
        ];

        return result.ToArray();
    }

    public static int MatchIdentifier(ArraySegment<byte> text)
    {
        return text.MatchRulesLength(
            c => char.IsAsciiLetter((char)c) || c == '_',
            (c) => char.IsLetterOrDigit((char)c) || c == '_' || c == '.'
        );
    }

    private static int MatchComment(ArraySegment<byte> text)
    {
        if (text.Count < 2)
        {
            return 0;
        }

        if (text[0] != '/' || text[1] != '/')
        {
            return 0;
        }

        Span<byte> span = text.Slice(2).AsSpan();
        int eolnPos = span.IndexOf((byte)'\r');
        return eolnPos == -1 ? span.Length + 2 : eolnPos + 2;
    }

    private static int MatchQuote(ArraySegment<byte> text)
    {
        if (text.Count < 2)
        {
            return 0;
        }

        Span<byte> span = text.AsSpan();

        byte firstChar = span[0];
        if (firstChar != '\'' && firstChar != '"')
        {
            return 0;
        }

        bool isEscaping = false;
        for (int index = 1; index < span.Length; index++)
        {
            if (isEscaping)
            {
                isEscaping = false;
                continue;
            }

            byte c = span[index];
            if (c == '\\')
            {
                isEscaping = true;
                continue;
            }

            if (c == firstChar)
            {
                return index + 1;
            }
        }

        return 0;
    }

    private static int MatchTemplate(ArraySegment<byte> text)
    {
        if (text[0] != '$')
        {
            return 0;
        }

        return 1 + MatchQuote(text.Slice(1));
    }

    private static int MatchOperator(ArraySegment<byte> text)
    {
        int matchAnyWord = LexerRulesUtilities.MatchAnyWord(text, Operators);
        return matchAnyWord;
    }

    private static int MatchEoln(ArraySegment<byte> text)
        => text.MatchAll(match => match == '\r' || match == '\n');

    private static int MatchNumber(ArraySegment<byte> text)
        => text.MatchAll(match => char.IsDigit((char)match));

    private static int MatchSpaces(ArraySegment<byte> text)
        => text.MatchAll(match => match == ' ' || match == '\t');
}