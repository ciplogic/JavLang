namespace SimLang.Common;

using static ResultExtensions;

public class FileUtils
{
    public static Result<byte[]> ReadAllBytes(string path)
    {
        byte[] content = File.ReadAllBytes(path);
        return Ok(content);
    }

    public static string[] FilesInDir(string dirPath, string extension)
    {
        string[] files = Directory.GetFiles(dirPath, "*" + extension, SearchOption.AllDirectories);
        return files;
    }
}