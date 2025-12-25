namespace SimLang.SyntaxParser.Common;

internal class BaseDeclaration(Permissions permissions)
{
    public Permissions Permissions { get; } = permissions;
    public List<BaseDeclaration> Children = [];
}