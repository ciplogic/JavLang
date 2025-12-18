using SimLang.Lexing;

internal static class LexerObject
{
    public static LexerRule[] Rules = BuildRules();

    private static LexerRule[] BuildRules()
    {
        List<LexerRule> result =
        [
            new (TokenType.Space, MatchSpaces)
        ];

        return result.ToArray();
    }

    private static int MatchRulesLength(ArraySegment<byte> segment, Predicate<byte> matchFirst,  Predicate<byte> matchRest)
    {
        var first = segment[0];
        if (!matchFirst(first))
        {
            return 0;
        }

        for (int i = 1; i < segment.Count; i++)
        {
            if (!matchRest(segment[i]))
            {
                return i;
            }
        }
        return segment.Count;
    }

    private static int MatchAll(ArraySegment<byte> segment, Predicate<byte> match)
        => MatchRulesLength(segment, match, match);

    private static int MatchSpaces(ArraySegment<byte> text) 
        => MatchAll(text, match => match == ' ' || match == '\t');
}