package main.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

/**
 * 提供文件操作的工具类，包括读取和写入文件。
 */
public class FileUtils {

    /**
     * 读取指定路径的文件内容。
     *
     * <p>此方法使用 UTF-8 编码读取文件内容。如果文件不存在或发生读取错误，将抛出 IOException。</p>
     *
     * @param filePath 文件的绝对路径或相对路径
     * @return 文件的内容，作为字符串
     * @throws IOException 如果读取文件时发生错误（如文件不存在或读取权限问题）
     */
    public static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        // 检查文件是否存在
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }
        // 读取文件内容并返回
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    /**
     * 将内容写入指定路径的文件。如果文件的父目录不存在，则创建相应的目录。
     *
     * <p>此方法会覆盖文件中的现有内容。如果指定路径指向一个目录，将抛出 IOException。</p>
     *
     * @param filePath 文件的绝对路径或相对路径
     * @param content  要写入的内容
     * @throws IOException 如果写入文件时发生错误（如文件系统问题或权限问题）
     */
    public static void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        // 检查是否是目录
        if (Files.isDirectory(path)) {
            throw new IOException("Path points to a directory, not a file: " + filePath);
        }
        // 创建父目录，如果不存在的话
        Files.createDirectories(path.getParent());
        // 写入文件内容
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}