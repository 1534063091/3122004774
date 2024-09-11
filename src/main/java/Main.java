package main;

import main.Utils.StringUtils;
import main.Utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 主类，负责从命令行接收参数，读取文件内容，计算文件内容的相似度，并将结果写入指定的输出文件。
 */
public class Main {
    // 创建 Logger 实例
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * 程序入口点。
     *
     * <p>接收三个命令行参数：原始文件路径、抄袭文件路径和输出文件路径。读取原始文件和抄袭文件的内容，
     * 计算它们之间的相似度，并将相似度百分比写入输出文件。</p>
     *
     * @param args 命令行参数数组
     */
    public static void main(String[] args) {
        // 检查参数数量是否正确
        if (args.length != 3) {
            System.out.println("Usage: java -jar main.jar <orig_file> <plagiarized_file> <output_file>");
            return;
        }

        String origFilePath = args[0];
        String plagiarizedFilePath = args[1];
        String outputFilePath = args[2];

        try {
            // 验证原始文件是否存在
            Path origPath = Paths.get(origFilePath);
            if (!Files.exists(origPath)) {
                logger.severe("Original file does not exist: " + origFilePath);
                return;
            }

            // 验证抄袭文件是否存在
            Path plagiarizedPath = Paths.get(plagiarizedFilePath);
            if (!Files.exists(plagiarizedPath)) {
                logger.severe("Plagiarized file does not exist: " + plagiarizedFilePath);
                return;
            }

            // 读取原始文件和抄袭文件的内容
            String originalText = FileUtils.readFile(origFilePath);
            String plagiarizedText = FileUtils.readFile(plagiarizedFilePath);

            // 计算两个文本的相似度
            double similarity = StringUtils.calculateSimilarity(originalText, plagiarizedText);

            // 将相似度结果格式化为保留两位小数的字符串，并写入输出文件
            String result = String.format("%.2f", similarity);
            FileUtils.writeFile(outputFilePath, result);

            logger.info("Similarity written to " + outputFilePath);

        } catch (IOException e) {
            // 捕获和处理文件操作中的异常，使用日志记录异常信息
            logger.log(Level.SEVERE, "An error occurred while processing files", e);
        }
    }
}