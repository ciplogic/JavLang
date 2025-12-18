package lt.khlud.ciprian.javlang.parse.semantic;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.lex.common.TokenType;

import java.util.ArrayList;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;

public class SemanticAstParser {
    public Res<CompilationUnit> parse(String fileName, ITokenProvider tokenProvider) {

        CompilationUnit result = new CompilationUnit();
        Res<Boolean> resultAdv = getPackageName(tokenProvider, result);
        if (resultAdv.isErr()) return resultAdv.errOf();

        Res<Boolean> checkImport = CheckImports(tokenProvider, result);
        if (checkImport.isErr()) return checkImport.errOf();

        AccessorsReader accessorsReader = new AccessorsReader();
        Res<ArrayList<String>> accessors = accessorsReader.readAccessors(tokenProvider);

        return parseTopDefinition(tokenProvider, result);
    }

    private Res<CompilationUnit> parseTopDefinition(ITokenProvider tokenProvider, CompilationUnit result) {
        var currentToken2 = tokenProvider.advance();
        if (currentToken2.value().kind() != TokenType.Reserved) {
            return Res.err("Expected reserved token");
        }

        switch (currentToken2.value().text()) {
            case "enum":
                return parseEnumDefinition(tokenProvider, result);
            default:
                return Res.err("Unexpected token: " + currentToken2.value().text());
        }
    }

    private Res<CompilationUnit> parseEnumDefinition(ITokenProvider tokenProvider, CompilationUnit result) {
        var tokensResult = tokenProvider.readUntil("}");
        if (tokensResult.isErr()) {
            return tokensResult.errOf();
        }
        var enumDefinitionResult = EnumDefinition.parseFromTokens(tokensResult.value());
        if (enumDefinitionResult.isErr()) {
            return enumDefinitionResult.errOf();
        }
        result.addEnumDefinition(enumDefinitionResult.value());
        return Res.ok(result);
    }

    private Res<Boolean> CheckImports(ITokenProvider tokenProvider, CompilationUnit result) {
        do {
            Res<Boolean> checkImport = tokenProvider.advanceIf("import");
            if (checkImport.isErr()) {
                return checkImport.errOf();
            }

            if (!checkImport.value()) {
                break;
            }
            getImport(tokenProvider, result);
        } while (true);
        return Res.ok(true);
    }

    private Res<Boolean> getImport(ITokenProvider tokenProvider, CompilationUnit result) {
        Res<ArrayList<Token>> tokensUntilSemicolon = tokenProvider.readUntil(";");
        if (!tokensUntilSemicolon.isOk()) {
            return tokensUntilSemicolon.errOf();
        }
        var tokens = tokensUntilSemicolon.value();
        var view = toView(tokens).trimEnd(1).mapTo(Token::text);
        result.addImport(view.join(""));
        return Res.ok(true);

    }

    private static Res<Boolean> getPackageName(ITokenProvider tokenProvider, CompilationUnit result) {
        Res<Boolean> resultAdv = tokenProvider.advanceIf("package");
        if (!resultAdv.isOk()) {
            return resultAdv.errOf();
        }

        Res<ArrayList<Token>> tokensUntilSemicolon = tokenProvider.readUntil(";");
        if (!tokensUntilSemicolon.isOk()) {
            return tokensUntilSemicolon.errOf();
        }

        var tokens = tokensUntilSemicolon.value();
        var view = toView(tokens).trimEnd(1).mapTo(Token::text);
        result.packageName = view.join("");
        return Res.ok(true);

    }
}

