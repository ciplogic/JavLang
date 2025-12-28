using SimLang.Common;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.Declarations;

internal class NamedDeclaration(U8Str name) : BaseDeclaration(name, new Permissions([]))
{
    public override string ToString()
        => Name.ToString();
}