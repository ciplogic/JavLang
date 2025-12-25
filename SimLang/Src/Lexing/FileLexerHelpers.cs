using System.Text;
using SimLang.Common;

namespace SimLang.Lexing;

public static class FileLexerHelpers
{
    internal static Result<Token[]> GetTokens(string filePath, Predicate<Token>? shouldSkip = null)
    {
        shouldSkip ??= IsTokenSkippable;
        List<Token> tokenList = new List<Token>();
        byte[] content = Encoding.UTF8.GetBytes(File.ReadAllText(filePath));

        Lexer lexer = new Lexer(content);
        do
        {
            Result<Token> token = lexer.Advance();
            if (token.IsError)
            {
                Console.WriteLine($"Lexer error: {token.ErrorMessage} for filename {filePath}");
                return token.ErrorOf<Token[]>();
                break;
            }

            if (token.Value.Type == TokenType.None)
            {
                break;
            }

            if (shouldSkip(token.Value))
            {
                continue;
            }

            tokenList.Add(token.Value);
        } while (true);

        return ResultExtensions.Ok(tokenList.ToArray());
    }

    internal static bool IsTokenSkippable(Token token) =>
        token.Type switch
        {
            TokenType.Eoln => true,
            TokenType.Comment => true,
            TokenType.Space => true,
            _ => false
        };
}