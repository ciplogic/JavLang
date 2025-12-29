using SimLang.Common;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.Declarations;

internal class BaseDeclaration(U8Str name, Permissions permissions)
{
    public U8Str Name { get; } = name;
    public Permissions Permissions { get; set; } = permissions;
    public List<BaseDeclaration> Children { get; } = [];
    
    public override string ToString()
        => $"{GetType().Name} {Name}";
}