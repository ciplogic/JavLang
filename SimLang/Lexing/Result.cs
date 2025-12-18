record struct Result<T>
{
    public T Value;
    public string ErrorMessage;
    public static Result<T> Ok(T value) => new Result<T> { Value = value, ErrorMessage = null! };
    public static Result<T> Error(string errorMessage) => new Result<T> { Value = default!, ErrorMessage = errorMessage };
    public bool Success => ErrorMessage != null;
}