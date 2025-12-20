package lt.khlud.ciprian.javlang.parse.semantic;

import lt.khlud.ciprian.javlang.common.Res;
import lt.khlud.ciprian.javlang.lex.common.ITokenProvider;
import lt.khlud.ciprian.javlang.lex.common.Token;
import lt.khlud.ciprian.javlang.lex.common.TokenType;
import lt.khlud.ciprian.javlang.parse.semantic.declarations.EnumDefinition;
import lt.khlud.ciprian.javlang.parse.semantic.declarations.InterfaceDefinition;

import java.util.ArrayList;

import static lt.khlud.ciprian.javlang.common.ListViewUtilities.toView;
import static lt.khlud.ciprian.javlang.lex.ArrayScannerUtils.getAccessors;

public class SemanticAstParser {
    public Res<CompilationUnit> parse(String fileName, ITokenProvider tokenProvider) {

        CompilationUnit result = new CompilationUnit();
        Res<Boolean> resultAdv = getPackageName(tokenProvider, result);
        if (resultAdv.isErr()) return resultAdv.errOf();

        Res<Boolean> checkImport = CheckImports(tokenProvider, result);
        if (checkImport.isErr()) return checkImport.errOf();

        return parseTopDefinition(tokenProvider, result);
    }

    private Res<CompilationUnit> parseTopDefinition(ITokenProvider tokenProvider, CompilationUnit result) {
        Res<ArrayList<String>> accessors = getAccessors(tokenProvider);
        if (accessors.isErr()) {
            return accessors.errOf();
        }

        var currentToken2 = tokenProvider.advance();
        if (currentToken2.value().kind() != TokenType.Reserved) {
            return Res.err("Expected reserved token");
        }

        switch (currentToken2.value().text()) {
            case "enum":
                return parseEnumDefinition(tokenProvider, result, accessors.value());
            case "interface":
                return parseInterfaceDefinition(tokenProvider, result, accessors.value());
            case "record":
                return parseRecordDefinition(tokenProvider, result, accessors.value());
            default:
                return Res.err("Unexpected token: " + currentToken2.value().text());
        }
    }

    private Res<CompilationUnit> parseInterfaceDefinition(ITokenProvider tokenProvider, CompilationUnit result, ArrayList<String> accessorsReader) {
        var tokensResult = tokenProvider.readUntil("}");
        if (tokensResult.isErr()) {
            return tokensResult.errOf();
        }
        var enumDefinitionResult = InterfaceDefinition.parseFromTokens(tokensResult.value(), accessorsReader);
        if (enumDefinitionResult.isErr()) {
            return enumDefinitionResult.errOf();
        }
        result.addInterfaceDefinition(enumDefinitionResult.value());
        return Res.ok(result);
    }

    private Res<CompilationUnit> parseRecordDefinition(ITokenProvider tokenProvider, CompilationUnit result, ArrayList<String> accessorsReader) {
        var tokensResult = tokenProvider.readUntil("}");
        if (tokensResult.isErr()) {
            return tokensResult.errOf();
        }
        var enumDefinitionResult = EnumDefinition.parseFromTokens(tokensResult.value(), accessorsReader);
        if (enumDefinitionResult.isErr()) {
            return enumDefinitionResult.errOf();
        }
        result.addEnumDefinition(enumDefinitionResult.value());
        return Res.ok(result);
    }

    private Res<CompilationUnit> parseEnumDefinition(ITokenProvider tokenProvider, CompilationUnit result, ArrayList<String> accessorsReader) {
        var tokensResult = tokenProvider.readUntil("}");
        if (tokensResult.isErr()) {
            return tokensResult.errOf();
        }
        var enumDefinitionResult = EnumDefinition.parseFromTokens(tokensResult.value(), accessorsReader);
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

