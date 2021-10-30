import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * 根据日期生成文件 202012目录，下面生成20201201.md ~ 20201231.md文件
 *
 * @author: guangxush
 * @create: 2020/11/14
 */
public class GenerateFileByDate {
    public static void main(String[] args) {
        convertReadMeFile();
        GenerateFileByDate date = new GenerateFileByDate();
        long prefix = Long.valueOf(getTodayYearMonthDay());
        System.out.println(prefix);
        prefix = 20211001;
        while (prefix <= 20211030) {
            createFile(prefix + ".md");
            prefix++;
        }
        // 列出当前目录下创建的所有文件
        tree(new File("./" + String.valueOf(prefix).substring(0, 6)), 1);

        // 如果需要写入内容，可以通过以下方式写入
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("## Algorithm\n" +
                "\n" +
                "### Description\n" +
                "\n" +
                "### Solution\n" +
                "\n" +
                "```java \n" +
                "\n" +
                "```\n" +
                "\n" +
                "### Discuss\n" +
                "\n" +
                "## Review\n" +
                "\n" +
                "\n" +
                "## Tip\n" +
                "\n" +
                "\n" +
                "## Share\n");
        try {
            writeAll(new File("./" + String.valueOf(prefix).substring(0, 6)), 1, stringBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转换readme文件
     */
    public static void convertReadMeFile() {
        String before = "| [20210928](./202109/20210928.md) |[334. Increasing Triplet Subsequence(递增的三元子序列)](https://leetcode.com/problems/increasing-triplet-subsequence/)||||\n" +
                "| [20210927](./202109/20210927.md) |[226. Invert Binary Tree(二叉树反转)](https://leetcode.com/problems/invert-binary-tree/)||||\n" +
                "| [20210926](./202109/20210926.md) |[224. Basic Calculator(基本计算器)](https://leetcode.com/problems/basic-calculator/)||||\n" +
                "| [20210925](./202109/20210925.md) |[114. Flatten Binary Tree to Linked List(二叉树展开为链表)](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)||||\n" +
                "| [20210923](./202109/20210923.md) |[24. Swap Nodes in Pairs(两两交换链表中的节点)](https://leetcode.com/problems/swap-nodes-in-pairs/)||||\n" +
                "| [20210922](./202109/20210922.md) |[78. Subsets(子集)](https://leetcode.com/problems/subsets/)||||\n" +
                "| [20210917](./202109/20210917.md) |[22. Generate Parentheses(生成括号)](https://leetcode.com/problems/generate-parentheses/)||||\n" +
                "| [20210916](./202109/20210916.md) |[128. Longest Consecutive Sequence(最长连续序列)](https://leetcode.com/problems/longest-consecutive-sequence/)||||\n" +
                "| [20210915](./202109/20210915.md) |[79. Word Search(单词搜索)](https://leetcode.com/problems/word-search/)||||\n" +
                "| [20210914](./202109/20210914.md) |[5. Longest Palindromic Substring(最长回文子串)](https://leetcode.com/problems/longest-palindromic-substring/)||||\n" +
                "| [20210913](./202109/20210913.md) |[958. Check Completeness of a Binary Tree(二叉树的完全性检验)](https://leetcode.com/problems/check-completeness-of-a-binary-tree/)||||\n" +
                "| [20210912](./202109/20210912.md) |[32. Longest Valid Parentheses(最长有效括号)](https://leetcode.com/problems/longest-valid-parentheses/)||||\n" +
                "| [20210910](./202109/20210910.md) |[234. Palindrome Linked List(回文链表)](https://leetcode.com/problems/palindrome-linked-list/)||||\n" +
                "| [20210909](./202109/20210909.md) |[48. Rotate Image(旋转图像)](https://leetcode.com/problems/rotate-image/)||||\n" +
                "| [20210908](./202109/20210908.md) |[162. Find Peak Element(发现山峰元素)](https://leetcode.com/problems/find-peak-element/)||||\n" +
                "| [20210907](./202109/20210907.md) |[322. Coin Change(零钱兑换)](https://leetcode.com/problems/coin-change/)||||\n" +
                "| [20210906](./202109/20210906.md) |[39. Combination Sum(组合总和)](https://leetcode.com/problems/combination-sum/)||||\n" +
                "| [20210905](./202109/20210905.md) |[98. Validate Binary Search Tree(验证二叉搜索树)](https://leetcode.com/problems/validate-binary-search-tree/)||||";

        // | [20200807](./guangxu/202008/20200807.md) | [303. Range Sum Query - Immutable（数组范围内求和）](https://leetcode.com/problems/range-sum-query-immutable/) |
        String[] beforeArray = before.split("\n");
        StringBuffer after = new StringBuffer();
        for(int i=0;i<beforeArray.length;i++){
            String temp = beforeArray[i];
            StringBuffer result = new StringBuffer();
            for(int j = 0; j < 3; j++){
                String str1=temp.substring(0, temp.indexOf("|")+1);
                result.append(str1);
                temp=temp.substring(str1.length());
            }
            temp = result.toString();
            temp = temp.replace("(./", "(./guangxu/");
            after.append(temp).append("\n");
            System.out.println(temp);
        }
    }

    /**
     * 将当前目录下所有文件追加统一的字符串
     *
     * @param file
     * @param level
     */
    public static void writeAll(File file, int level, StringBuffer stringBuffer) throws IOException {
        File[] childs = file.listFiles();
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) {
                tree(childs[i], level + 1);
            }
            writeTextToFile(childs[i], stringBuffer);
        }
    }

    /**
     * 向指定文件写入内容
     * @param checkFile
     * @param stringBuffer
     * @throws IOException
     */
    public static void writeTextToFile(File checkFile, StringBuffer stringBuffer) throws IOException {
        FileWriter writer = null;
        try {
            // 1、检查目标文件是否存在，不存在则创建
            if (!checkFile.exists()) {
                checkFile.createNewFile();// 创建目标文件
            }
            // 2、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(checkFile, false);
            writer.append(stringBuffer);
            writer.flush();
            System.out.println(checkFile.getName() + "写入成功！！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    /**
     * 根据文件名创建文件
     * @param filename
     */
    public static void createFile(String filename) {
        File file = new File("./" + filename.substring(0, 6), filename);
        if (file.exists()) {
            System.out.println("文件名:" + file.getAbsolutePath());
            System.out.println("文件大小：" + file.length());
        } else {
            //在.class文件所在的最上层包的父路径下
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归列举出所有文件
     *
     * @param file
     * @param level
     */
    public static void tree(File file, int level) {
        String preStr = "";
        for (int i = 0; i < level; i++) {
            //为了看的分层更清楚，建议多敲几个空格
            preStr += "    ";
        }
        //列出当前目录的孩子们
        File[] childs = file.listFiles();
        for (int i = 0; i < childs.length; i++) {
            System.out.println(preStr + childs[i].getName());
            if (childs[i].isDirectory()) {
                tree(childs[i], level + 1);
            }
        }
    }

    /**
     * 获取日期前缀
     *
     * @return
     */
    public static String getTodayYearMonthDay() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH) + 1;//获取月份+1
        int day = cal.get(Calendar.DATE);//获取日
        return String.valueOf(year) + (month) + (day);
    }

    public void getTimeByCalendar() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH);//获取月份+1
        int day = cal.get(Calendar.DATE);//获取日
        int hour = cal.get(Calendar.HOUR);//小时
        int minute = cal.get(Calendar.MINUTE);//分
        int second = cal.get(Calendar.SECOND);//秒
        int WeekOfYear = cal.get(Calendar.DAY_OF_WEEK);//一周的第几天
        System.out.println("现在的时间是：公元" + year + "年" + month + "月" + day + "日      " + hour + "时" + minute + "分" + second + "秒       星期" + WeekOfYear);
    }
}
