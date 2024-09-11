package test.java;

import main.Main;
import main.Utils.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试类 MainTest，负责测试 Main 类的功能。
 * 使用 JUnit 框架，通过临时文件夹和文件验证 Main 类的相似度计算功能。
 */
public class MainTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * 测试两个文件内容完全相同的情况。
     * <p>
     * 此测试方法创建两个内容完全相同的临时文件，执行 Main.main() 方法，
     * 然后验证输出的相似度百分比是否为 100%。
     * </p>
     *
     * @throws IOException 如果读取或写入文件时发生错误
     */
    @Test
    public void testFilesWithSameContent() throws IOException {
        // 创建临时文件
        File origFile = tempFolder.newFile("orig.txt");
        File plagiarizedFile = tempFolder.newFile("plagiarized.txt");
        File outputFile = tempFolder.newFile("result.txt");

        // 写入相同内容
        FileUtils.writeFile(origFile.getAbsolutePath(), "这是一段测试文本。");
        FileUtils.writeFile(plagiarizedFile.getAbsolutePath(), "这是一段测试文本。");

        // 执行主程序
        String[] args = {
                origFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };
        Main.main(args);

        // 验证结果
        assertTrue("Output file should be created.", outputFile.exists());
        String result = FileUtils.readFile(outputFile.getAbsolutePath());
        assertEquals("100.00", result);
    }

    /**
     * 测试两个文件内容不同的情况。
     * <p>
     * 此测试方法创建两个内容不同的临时文件，执行 Main.main() 方法，
     * 然后验证输出文件是否被创建以及输出的相似度百分比是否符合预期。
     * </p>
     *
     * @throws IOException 如果读取或写入文件时发生错误
     */
    @Test
    public void testFilesWithDifferentContent() throws IOException {
        // 创建临时文件
        File origFile = tempFolder.newFile("orig.txt");
        File plagiarizedFile = tempFolder.newFile("plagiarized.txt");
        File outputFile = tempFolder.newFile("result.txt");

        // 写入测试数据
        FileUtils.writeFile(origFile.getAbsolutePath(), "今天是星期天，天气晴，今天晚上我要去看电影。");
        FileUtils.writeFile(plagiarizedFile.getAbsolutePath(), "今天是周天，天气晴朗，我晚上要去看电影。");

        // 执行主程序
        String[] args = {
                origFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };
        Main.main(args);

        // 验证结果
        // 确保输出文件被创建
        assertTrue("Output file should be created.", outputFile.exists());

        // 读取输出文件内容
        String result = FileUtils.readFile(outputFile.getAbsolutePath());

        // 验证相似度百分比是否正确，考虑计算误差
        assertEquals("72.73", result);
    }

    /**
     * 测试一个空文件和一个非空文件的情况。
     * <p>
     * 此测试方法创建一个空文件和一个非空文件，执行 Main.main() 方法，
     * 然后验证输出的相似度百分比是否为 0%。
     * </p>
     *
     * @throws IOException 如果读取或写入文件时发生错误
     */
    @Test
    public void testEmptyFileAndNonEmptyFile() throws IOException {
        // 创建临时文件
        File origFile = tempFolder.newFile("orig.txt");
        File plagiarizedFile = tempFolder.newFile("plagiarized.txt");
        File outputFile = tempFolder.newFile("result.txt");

        // 写入非空内容
        FileUtils.writeFile(plagiarizedFile.getAbsolutePath(), "这是一段测试文本。");

        // 执行主程序
        String[] args = {
                origFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };
        Main.main(args);

        // 验证结果
        assertTrue("Output file should be created.", outputFile.exists());
        String result = FileUtils.readFile(outputFile.getAbsolutePath());
        assertEquals("0.00", result);
    }

    /**
     * 测试两个空文件的情况。
     * <p>
     * 此测试方法创建两个空文件，执行 Main.main() 方法，
     * 然后验证输出的相似度百分比是否为 100%。
     * </p>
     *
     * @throws IOException 如果读取或写入文件时发生错误
     */
    @Test
    public void testTwoEmptyFiles() throws IOException {
        // 创建临时文件
        File origFile = tempFolder.newFile("orig.txt");
        File plagiarizedFile = tempFolder.newFile("plagiarized.txt");
        File outputFile = tempFolder.newFile("result.txt");

        // 执行主程序
        String[] args = {
                origFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };
        Main.main(args);

        // 验证结果
        assertTrue("Output file should be created.", outputFile.exists());
        String result = FileUtils.readFile(outputFile.getAbsolutePath());
        assertEquals("100.00", result);
    }

    /**
     * 测试两个单字符文件的情况。
     * <p>
     * 此测试方法创建两个仅包含一个字符的文件，执行 Main.main() 方法，
     * 然后验证输出的相似度百分比是否为 100%。
     * </p>
     *
     * @throws IOException 如果读取或写入文件时发生错误
     */
    @Test
    public void testSingleCharacterFiles() throws IOException {
        // 创建临时文件
        File origFile = tempFolder.newFile("orig.txt");
        File plagiarizedFile = tempFolder.newFile("plagiarized.txt");
        File outputFile = tempFolder.newFile("result.txt");

        // 写入单字符内容
        FileUtils.writeFile(origFile.getAbsolutePath(), "a");
        FileUtils.writeFile(plagiarizedFile.getAbsolutePath(), "a");

        // 执行主程序
        String[] args = {
                origFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };
        Main.main(args);

        // 验证结果
        assertTrue("Output file should be created.", outputFile.exists());
        String result = FileUtils.readFile(outputFile.getAbsolutePath());
        assertEquals("100.00", result);
    }
}