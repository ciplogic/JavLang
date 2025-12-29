using SimLang.Common;

namespace SimLang.SyntaxParser.Declarations;

class NamedGenericDeclaration(U8Str name) : NamedDeclaration(name)
{
    public List<BaseDeclaration> Generics = [];
}