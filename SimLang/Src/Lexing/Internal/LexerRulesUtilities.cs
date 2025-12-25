using System.Text;

namespace SimLang.Lexing.Internal;

public static class LexerRulesUtilities
{
    public static int MatchRulesLength(this ArraySegment<byte> segment, Predicate<byte> matchFirst,  Predicate<byte> matchRest)
    {
        byte first = segment[0];
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

    public static int MatchAll(this ArraySegment<byte> segment, Predicate<byte> match)
        => MatchRulesLength(segment, match, match);
    
    
    public static string CalculateRemainingText(ArraySegment<byte> remainingSegment)
    {
        Span<byte> remainingSlice = remainingSegment.AsSpan();
        int eolnPos = remainingSlice.IndexOf((byte)'\r');
        if (eolnPos > 0)
        {
            remainingSlice = remainingSlice.Slice(0, eolnPos);
        }

        return Encoding.UTF8.GetString(remainingSlice);
    }


    public static int MatchAnyWord(ArraySegment<byte> text, string[] operators)
    {
        foreach (string op in operators)
        {
            if (text.Count < op.Length)
            {
                continue;
            }

            bool isMatching = true;
            for (int index = 0; index < op.Length; index++)
            {
                char ch = op[index];
                if (text[index] != ch)
                {
                    isMatching = false;
                    break;
                }
            }

            if (isMatching)
            {
                return op.Length;
            }
        }

        return 0;
    }
}