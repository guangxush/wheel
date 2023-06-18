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
        // convertReadMeFile();
        GenerateFileByDate date = new GenerateFileByDate();
        long prefix = Long.valueOf(getTodayYearMonthDay());
        System.out.println(prefix);
        prefix = 20231201;
        while (prefix <= 20231230) {
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
        String before = "| [20221226](./202212/20221226.md) |[15. 3Sum(三数之和)](https://leetcode.com/problems/3sum/)||||\n" +
                "| [20221225](./202212/20221225.md) |[94. Binary Tree Inorder Traversal(二叉树中序遍历)](https://leetcode.com/problems/binary-tree-inorder-traversal/)||||\n" +
                "| [20221224](./202212/20221224.md) |[110. Balanced Binary Tree(平衡二叉树)](https://leetcode.com/problems/balanced-binary-tree/)||||\n" +
                "| [20221223](./202212/20221223.md) |[468. Validate IP Address(验证IP地址)](https://leetcode.com/problems/validate-ip-address/)||||\n" +
                "| [20221222](./202212/20221222.md) |[224. Basic Calculator(基本计算器)](https://leetcode.com/problems/basic-calculator/)||||\n" +
                "| [20221221](./202212/20221221.md) |[47. Permutations II(全排列2)](https://leetcode.com/problems/permutations-ii/)||||\n" +
                "| [20221220](./202212/20221220.md) |[22. Generate Parentheses(生成括号)](https://leetcode.com/problems/generate-parentheses/)||||\n" +
                "| [20221219](./202212/20221219.md) |[450. Delete Node in a BST(删除二叉搜索树的节点)](https://leetcode.com/problems/delete-node-in-a-bst/)||||\n" +
                "| [20221218](./202212/20221218.md) |[146. LRU Cache(LRU缓存)](https://leetcode.com/problems/lru-cache/)||||\n" +
                "| [20221217](./202212/20221217.md) |[297. Serialize and Deserialize Binary Tree(二叉树序列化与反序列化)](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)||||\n" +
                "| [20221216](./202212/20221216.md) |[543. Diameter of Binary Tree(二叉树的直径)](https://leetcode.com/problems/diameter-of-binary-tree/)||||\n" +
                "| [20221215](./202212/20221215.md) |[151. Reverse Words in a String(字符串中单词反转)](https://leetcode.com/problems/reverse-words-in-a-string/)||||\n" +
                "| [20221214](./202212/20221214.md) |[91. Decode Ways(解码方法)](https://leetcode.com/problems/decode-ways/)||||\n" +
                "| [20221213](./202212/20221213.md) |[53. Maximum Subarray(最大子段和)](https://leetcode.com/problems/maximum-subarray/)||||\n" +
                "| [20221212](./202212/20221212.md) |[124. Binary Tree Maximum Path Sum(二叉树最大路径和)](https://leetcode.com/problems/binary-tree-maximum-path-sum/)||||\n" +
                "| [20221211](./202212/20221211.md) |[48. Rotate Image(旋转图像)](https://leetcode.com/problems/rotate-image/)||||\n" +
                "| [20221210](./202212/20221210.md) |[206. Reverse Linked List(反转链表)](https://leetcode.com/problems/reverse-linked-list/description/)||||\n" +
                "| [20221209](./202212/20221209.md) |[236. Lowest Common Ancestor of a Binary Tree(二叉树最小公共祖先)](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/)||||\n" +
                "| [20221208](./202212/20221208.md) |[215. Kth Largest Element in an Array(数组中第K个最大元素)](https://leetcode.com/problems/kth-largest-element-in-an-array/)||||\n" +
                "| [20221207](./202212/20221207.md) |[149. Max Points on a Line(同一条线上最多的点)](https://leetcode.com/problems/max-points-on-a-line/)||||\n" +
                "| [20221206](./202212/20221206.md) |[68. Text Justification(文本对齐)](https://leetcode.com/problems/text-justification/)||||\n" +
                "| [20221205](./202212/20221205.md) |[29. Divide Two Integers(两个整数相除)](https://leetcode.com/problems/divide-two-integers/)||||\n" +
                "| [20221204](./202212/20221204.md) |[6. Zigzag Conversion(之字形转换)](https://leetcode.com/problems/zigzag-conversion/)||||\n" +
                "| [20221203](./202212/20221203.md) |[59. Spiral Matrix II(螺旋矩阵2)](https://leetcode.com/problems/spiral-matrix-ii/)||||\n" +
                "| [20221202](./202212/20221202.md) |[54. Spiral Matrix(螺旋矩阵)](https://leetcode.com/problems/spiral-matrix/)||||\n" +
                "| [20221201](./202212/20221201.md) |[119. Pascal's Triangle II(杨辉三角2)](https://leetcode.com/problems/pascals-triangle-ii/)||||\n" +
                "| [20221130](./202211/20221130.md) |[118. Pascal's Triangle(杨辉三角)](https://leetcode.com/problems/pascals-triangle/)||||\n" +
                "| [20221129](./202211/20221129.md) |[30. Substring with Concatenation of All Words(串联所有单词的子串)](https://leetcode.com/problems/substring-with-concatenation-of-all-words/)||||\n" +
                "| [20221128](./202211/20221128.md) |[43. Multiply Strings(字符串相乘)](https://leetcode.com/problems/multiply-strings/description/)||||\n" +
                "| [20221127](./202211/20221127.md) |[76. Minimum Window Substring(最小窗口子串)](https://leetcode.com/problems/minimum-window-substring/)||||\n" +
                "| [20221126](./202211/20221126.md) |[56. Merge Intervals(合并区间)](https://leetcode.com/problems/merge-intervals/)||||\n" +
                "| [20221125](./202211/20221125.md) |[57. Insert Interval(插入区间)](https://leetcode.com/problems/insert-interval/)||||\n" +
                "| [20221124](./202211/20221124.md) |[9. Palindrome Number(回文数)](https://leetcode.com/problems/palindrome-number/)||||\n" +
                "| [20221123](./202211/20221123.md) |[7. Reverse Integer(整数反转)](https://leetcode.com/problems/reverse-integer/)||||\n" +
                "| [20221122](./202211/20221122.md) |[133. Clone Graph(克隆图)](https://leetcode.com/problems/clone-graph/)||||\n" +
                "| [20221121](./202211/20221121.md) |[140. Word Break II(断字2)](https://leetcode.com/problems/word-break-ii/)||||\n" +
                "| [20221120](./202211/20221120.md) |[139. Word Break(断字)](https://leetcode.com/problems/word-break/)||||\n" +
                "| [20221119](./202211/20221119.md) |[115. Distinct Subsequences(不同子序列)](https://leetcode.com/problems/distinct-subsequences/)||||\n" +
                "| [20221118](./202211/20221118.md) |[91. Decode Ways(解码方法)](https://leetcode.com/problems/decode-ways/)||||\n" +
                "| [20221117](./202211/20221117.md) |[72. Edit Distance(编辑距离)](https://leetcode.com/problems/edit-distance/)||||\n" +
                "| [20221116](./202211/20221116.md) |[64. Minimum Path Sum(最小路径和)](https://leetcode.com/problems/minimum-path-sum/)||||\n" +
                "| [20221115](./202211/20221115.md) |[87. Scramble String(可拼凑字符串)](https://leetcode.com/problems/scramble-string/)||||\n" +
                "| [20221114](./202211/20221114.md) |[97. Interleaving String(交错字符串)](https://leetcode.com/problems/interleaving-string/)||||\n" +
                "| [20221113](./202211/20221113.md) |[123. Best Time to Buy and Sell Stock III(买卖股票的最佳时间3)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/)||||\n" +
                "| [20221112](./202211/20221112.md) |[85. Maximal Rectangle(最大矩形)](https://leetcode.com/problems/maximal-rectangle/)||||\n" +
                "| [20221111](./202211/20221111.md) |[132. Palindrome Partitioning II(回文分割算法2)](https://leetcode.com/problems/palindrome-partitioning-ii/)||||\n" +
                "| [20221110](./202211/20221110.md) |[53. Maximum Subarray(最大子段和)](https://leetcode.com/problems/maximum-subarray/)||||\n" +
                "| [20221109](./202211/20221109.md) |[120. Triangle(三角形)](https://leetcode.com/problems/triangle/)||||\n" +
                "| [20221108](./202211/20221108.md) |[11. Container With Most Water(容器最大水容量)](https://leetcode.com/problems/container-with-most-water/)||||\n" +
                "| [20221107](./202211/20221107.md) |[3. Longest Substring Without Repeating Characters(无重复最长子串)](https://leetcode.com/problems/longest-substring-without-repeating-characters/)||||\n" +
                "| [20221106](./202211/20221106.md) |[122. Best Time to Buy and Sell Stock II(买卖股票的最佳时机2)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/)||||\n" +
                "| [20221105](./202211/20221105.md) |[121. Best Time to Buy and Sell Stock(买卖股票的最佳时机)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)||||\n" +
                "| [20221104](./202211/20221104.md) |[45. Jump Game II(跳跃游戏2)](https://leetcode.com/problems/jump-game-ii/)||||\n" +
                "| [20221103](./202211/20221103.md) |[55. Jump Game(跳跃游戏)](https://leetcode.com/problems/jump-game/)||||\n" +
                "| [20221102](./202211/20221102.md) |[69. Sqrt(x)(求根号)](https://leetcode.com/problems/sqrtx/)||||\n" +
                "| [20221101](./202211/20221101.md) |[50. Pow(x, n)(求次幂)](https://leetcode.com/problems/powx-n/)||||";

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
