using SimLang.Common;

namespace SimLang.SyntaxParser;

internal record struct Using(U8Str Namespace, bool IsStatic);