using System.Text;

namespace SimLang.Lexing;

class Lexer
{
    private int _pos;
    private ArraySegment<byte> _buffer;

    public Lexer(string text) => _buffer = new ArraySegment<byte>(Encoding.UTF8.GetBytes(text));

    public Result<Token?> Advance()
    {
        if (_pos >= _buffer.Count)
        {
            return Result<Token?>.Ok(null);
        }

        LexerRule[] rules = LexerObject.Rules;
        ArraySegment<byte> readingSegment = _buffer.Slice(_pos);
        foreach (LexerRule lexerRule in rules)
        {
            int matchLen = lexerRule.Match(readingSegment);
            if (matchLen == 0)
            {
                continue;
            }

            var content = readingSegment.Slice(0, matchLen).ToArray();
            Token token = new Token(content, _pos, lexerRule.Type);
            _pos += matchLen;
            return Result<Token?>.Ok(token);
        }

        return Result<Token?>.Error("Lexer error" + RemainingText);
    }

    public string RemainingText
        => Encoding.UTF8.GetString(_buffer.Slice(_pos));
}