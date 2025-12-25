using System.Text;
using SimLang.Common;
using SimLang.Lexing.Internal;
using static SimLang.Common.ResultExtensions;

namespace SimLang.Lexing;

internal class Lexer
{
    private int _pos;
    private readonly ArraySegment<byte> _buffer;

    public Lexer(byte[] textBytes) => _buffer = new ArraySegment<byte>(textBytes);

    public Result<Token> Advance()
    {
        if (_pos >= _buffer.Count)
        {
            return Ok(Token.Empty);
        }

        LexerRule[] rules = LexerAllRules.Rules;
        ArraySegment<byte> readingSegment = _buffer.Slice(_pos);
        foreach (LexerRule lexerRule in rules)
        {
            int matchLen = lexerRule.Match(readingSegment);
            if (matchLen == 0)
            {
                continue;
            }

            ArraySegment<byte> tokenSlice = readingSegment.Slice(0, matchLen);
            U8Str tokenContent = new U8Str(tokenSlice.ToArray());

            Token token = new Token(tokenContent, _pos, lexerRule.Type);
            _pos += matchLen;
            return Ok(token);
        }

        return Error<Token>($"Lexer error: '{RemainingText}'");
    }

    public string RemainingText
        => LexerRulesUtilities.CalculateRemainingText(_buffer.Slice(_pos));
}