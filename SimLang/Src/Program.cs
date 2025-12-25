// See https://aka.ms/new-console-template for more information

using System.Text;
using SimLang.Common;
using SimLang.Lexing;

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

            Console.WriteLine(tokens.Value.Length);
        }
    }
}