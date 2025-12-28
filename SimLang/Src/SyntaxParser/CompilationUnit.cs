using SimLang.Common;
using SimLang.SyntaxParser.Common;
using SimLang.SyntaxParser.Declarations;

namespace SimLang.SyntaxParser;

internal class CompilationUnit(string filePath)
{
    public string FilePath { get; } = filePath;
    public U8Str Namespace { get; set; } = U8Str.Empty;

    List<Using> Usages = [];
    List<BaseDeclaration> Declarations = [];

    public void AddUsing(U8Str usingValue, bool isStatic)
        => Usages.Add(new Using(usingValue, isStatic));

    public void AddDeclaration(BaseDeclaration baseDeclaration)
        => Declarations.Add(baseDeclaration);

    override public string ToString()
    {
        if (Declarations.Count > 0)
        {
            return Declarations[0].ToString();
        }
        
        return FilePath;
    }
}