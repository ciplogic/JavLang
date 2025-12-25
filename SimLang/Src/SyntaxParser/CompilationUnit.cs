using SimLang.Common;

namespace SimLang.SyntaxParser;

internal class CompilationUnit(string filePath)
{
    public string FilePath { get; } = filePath;
    public U8Str Namespace { get; set; } = U8Str.Empty;

    List<Using> Usages = [];

    public void AddUsing(U8Str usingValue, bool isStatic)
    {
        Usages.Add(new Using(usingValue, isStatic));
    }
}