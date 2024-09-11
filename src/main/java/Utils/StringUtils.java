package main.Utils;

/**
 * 提供字符串操作的工具类，包括计算两个字符串的相似度百分比。
 */
public class StringUtils {

    /**
     * 计算两个字符串的相似度百分比。
     * 相似度基于 Damerau-Levenshtein 距离计算。
     *
     * <p>此方法使用 Damerau-Levenshtein 距离算法来衡量字符串之间的相似度，
     * 并将其转换为百分比表示形式。Damerau-Levenshtein 距离考虑插入、删除、替换和相邻字符交换四种操作。</p>
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 两个字符串之间的相似度百分比，范围从 0 到 100，其中 100 表示完全相同
     * @throws IllegalArgumentException 如果任一字符串为 null
     */
    public static double calculateSimilarity(String s1, String s2) {
        validateInputs(s1, s2);

        if (s1.equals(s2)) {
            return 100.0; // 完全匹配
        }

        int distance = computeDamerauLevenshteinDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return maxLength == 0 ? 100.0 : (1 - (double) distance / maxLength) * 100; // 计算百分比
    }

    /**
     * 验证输入字符串的有效性。
     *
     * <p>检查输入字符串是否为 null。如果任一字符串为 null，将抛出 {@link IllegalArgumentException}。</p>
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @throws IllegalArgumentException 如果任一字符串为 null
     */
    private static void validateInputs(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    /**
     * 计算两个字符串之间的 Damerau-Levenshtein 距离。
     * Damerau-Levenshtein 距离考虑插入、删除、替换和相邻字符交换四种操作。
     *
     * <p>使用动态规划算法计算两个字符串之间的最小编辑距离。</p>
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 两个字符串之间的 Damerau-Levenshtein 距离
     */
    private static int computeDamerauLevenshteinDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        // 确保 len1 <= len2，减少计算复杂度
        if (len1 > len2) {
            return computeDamerauLevenshteinDistance(s2, s1); // 交换参数
        }

        if (len1 == 0) return len2;

        int[] prevRow = new int[len1 + 1];
        int[] currRow = new int[len1 + 1];

        // 初始化距离矩阵的第一行
        initializeDistanceMatrix(prevRow, len1);

        for (int j = 1; j <= len2; j++) {
            currRow[0] = j;
            for (int i = 1; i <= len1; i++) {
                int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                currRow[i] = Math.min(Math.min(currRow[i - 1] + 1, prevRow[i] + 1), prevRow[i - 1] + cost);

                // 处理相邻字符交换
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2) && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    currRow[i] = Math.min(currRow[i], prevRow[i - 2] + cost);
                }
            }
            // 交换 prevRow 和 currRow 数组
            int[] temp = prevRow;
            prevRow = currRow;
            currRow = temp;
        }

        return prevRow[len1];
    }

    /**
     * 初始化距离矩阵的第一行。
     *
     * <p>此方法初始化矩阵的第一行，用于动态规划算法中的基础距离计算。</p>
     *
     * @param row   距离矩阵的第一行
     * @param length 行的长度
     */
    private static void initializeDistanceMatrix(int[] row, int length) {
        for (int i = 0; i <= length; i++) {
            row[i] = i;
        }
    }
}