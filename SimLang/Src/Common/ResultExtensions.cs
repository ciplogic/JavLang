namespace SimLang.Common;

static class ResultExtensions
{
    public static Result<T> Ok<T>(T value) => new Result<T>(value, string.Empty);

    public static Result<T> Error<T>(string errorMessage) => new(default!, errorMessage);
}