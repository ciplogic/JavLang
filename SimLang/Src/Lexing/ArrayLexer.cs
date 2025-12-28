namespace SimLang.Lexing;

public class ArrayLexer(ArraySegment<Token> readingSegment)
{
    ArraySegment<Token> ReadingSegment = readingSegment;

    public Token Peek()
    {
        if (ReadingSegment.Count == 0)
        {
            return Token.Empty;
        }

        return ReadingSegment[0];
    }

    public int Length => ReadingSegment.Count;

    public bool AdvanceIf(string text)
    {
        var token = Peek();
        if (token.Content != text)
        {
            return false;
        }

        Skip();
        return true;
    }

    public Token Advance()
    {
        var result = Peek();
        Skip();
        return result;
    }

    private void Skip() => ReadingSegment = ReadingSegment.Slice(1);
}