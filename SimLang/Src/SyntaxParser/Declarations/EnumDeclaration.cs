using SimLang.Common;
using SimLang.SyntaxParser.Common;

namespace SimLang.SyntaxParser.Declarations;

class EnumDeclaration(U8Str name, Permissions permissions)
    : BaseDeclaration(name, permissions)
{
}
