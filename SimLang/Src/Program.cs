// See https://aka.ms/new-console-template for more information

using SimLang.Common;
using SimLang.Lexing;
using SimLang.SyntaxParser;

namespace SimLang;

class Program
{
    static void Main(string[] args)
    {
        string[] files = FileUtils.FilesInDir(".", ".cs");
        foreach (string file in files)
        {
            var tokens = FileLexerHelpers.GetTokens(file);
            if (tokens.IsError)
            {
                Console.WriteLine($"Lexer error: {tokens.ErrorMessage} for filename {file}");
                continue;
            }

            var astParser = new SimpleAstParser();
            CompilationUnit parsedFile = astParser.Parse(file, tokens.Value);

            Console.WriteLine(tokens.Value.Length);
        }
    }
}