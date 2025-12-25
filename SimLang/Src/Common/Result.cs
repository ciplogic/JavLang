namespace SimLang.Common;

public record struct Result<T>(T Value, string ErrorMessage)
{
    public bool IsError => !string.IsNullOrEmpty(ErrorMessage);

    public Result<TOut> ErrorOf<TOut>() => ResultExtensions.Error<TOut>(ErrorMessage);
}