import org.junit.Assert;
import org.junit.Test;
/**
 * @author: guangxush
 * @create: 2020/10/21
 */
public class PatternMatch {

    public static void main(String[] args) {
        PatternMatch patternMatch = new PatternMatch();
        Assert.assertTrue(patternMatch.match("aaa","a.a"));
        Assert.assertTrue(patternMatch.match("aaa","ab*ac*a"));
        Assert.assertFalse(patternMatch.match("aaa","aa.a"));
        Assert.assertFalse(patternMatch.match("aaa","ab*a"));
    }

    public boolean match(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        return match(str.toCharArray(), 0, pattern.toCharArray(), 0);
    }

    /**
     * 每次分别在str 和pattern中取一个字符进行匹配，如果匹配，则匹配下一个字符，否则返回不匹配。
     * 设匹配递归函数 match(str, pattern)。
     * 如果模式匹配字符的下一个字符是‘*’:
     * - 如果pttern当前字符和str的当前字符匹配，：有以下三种可能情况
     * （1）pttern当前字符能匹配 str 中的 0 个字符：match(str, pattern+2)
     * （2）pttern当前字符能匹配 str 中的 1 个字符：match(str+1, pattern+2)
     * （3）pttern当前字符能匹配 str 中的 多 个字符：match(str+1, pattern)
     * - 如果pttern当前字符和和str的当前字符不匹配
     * pttern当前字符能匹配 str 中的 0 个字符：(str, pattern+2)
     * 如果模式匹配字符的下一个字符不是‘*’，进行逐字符匹配。
     * 对于 ‘.’ 的情况比较简单，’.’ 和一个字符匹配 match(str+1, pattern+1)
     * 另外需要注意的是：空字符串”” 和 “.*” 是匹配的
     * @param str
     * @param i
     * @param pattern
     * @param j
     * @return
     */
    private boolean match(char[] str, int i, char[] pattern, int j) {
        if (j == pattern.length) {
            return str.length == i;
        }
        if (j < pattern.length - 1 && pattern[j + 1] == '*') {
            if (str.length != i && (str[i] == pattern[j] || pattern[j] == '.')) {
                return match(str, i, pattern, j + 2) || match(str, i + 1, pattern, j);
            } else {
                return match(str, i, pattern, j + 2);
            }
        }
        if (str.length != i && (str[i] == pattern[j] || pattern[j] == '.')) {
            return match(str, i + 1, pattern, j + 1);
        }
        return false;
    }
}
