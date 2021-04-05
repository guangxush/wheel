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
        prefix = 20210401;
        while (prefix <= 20210430) {
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
        String before = "| [20210331](./202103/20210331.md) |[39. Combination Sum(组合总和)](https://leetcode.com/problems/combination-sum/)||||\n" +
                "| [20210330](./202103/20210330.md) |[198. House Robber(打家劫舍)](https://leetcode.com/problems/house-robber/)||||\n" +
                "| [20210329](./202103/20210329.md) |[215. Kth Largest Element in an Array(数组中的第K个最大元素)](https://leetcode.com/problems/kth-largest-element-in-an-array/)||||\n" +
                "| [20210328](./202103/20210328.md) |[103. Binary Tree Zigzag Level Order Traversal(二叉树之字形遍历)](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/)||||\n" +
                "| [20210327](./202103/20210327.md) |[700. Search in a Binary Search Tree(二叉树查询)](https://leetcode.com/problems/search-in-a-binary-search-tree/)||||\n" +
                "| [20210326](./202103/20210326.md) |[206. Reverse Linked List(链表反转)](https://leetcode.com/problems/reverse-linked-list/)||||\n" +
                "| [20210325](./202103/20210325.md) |[146. LRU Cache(LRU缓存机制)](https://leetcode.com/problems/lru-cache/)||||\n" +
                "| [20210324](./202103/20210324.md) |[121. Best Time to Buy and Sell Stock(买卖股票的最佳时间)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)||||\n" +
                "| [20210323](./202103/20210323.md) |[25. Reverse Nodes in k-Group(K个一组反转链表)](https://leetcode.com/problems/reverse-nodes-in-k-group/)||||\n" +
                "| [20210322](./202103/20210322.md) |[15. 3Sum(三数之和)](https://leetcode.com/problems/3sum/)||||\n" +
                "| [20210321](./202103/20210321.md) |[5. Longest Palindromic Substring(最长回文数)](https://leetcode.com/problems/longest-palindromic-substring/)||||\n" +
                "| [20210320](./202103/20210320.md) |[713. Subarray Product Less Than K(乘积小于K的子数组)](https://leetcode.com/problems/subarray-product-less-than-k/)||||\n" +
                "| [20210319](./202103/20210319.md) |[44. Wildcard Matching(通配符匹配)](https://leetcode.com/problems/wildcard-matching/)||||\n" +
                "| [20210318](./202103/20210318.md) |[39. Combination Sum(组合总和)](https://leetcode.com/problems/combination-sum/)||||\n" +
                "| [20210317](./202103/20210317.md) |[394. Decode String(字符串解码)](https://leetcode.com/problems/decode-string/)||||\n" +
                "| [20210316](./202103/20210316.md) |[88. Merge Sorted Array(合并两个有序数组)](https://leetcode.com/problems/merge-sorted-array/)||||\n" +
                "| [20210315](./202103/20210315.md) |[449. Serialize and Deserialize BST(二叉树的序列化与反序列化)](https://leetcode.com/problems/serialize-and-deserialize-bst/)|||好好准备|\n" +
                "| [20210314](./202103/20210314.md) |[3. Longest Substring Without Repeating Characters(无重复字符的最长子串)](https://leetcode.com/problems/longest-substring-without-repeating-characters/)||||\n" +
                "| [20210313](./202103/20210313.md) |[704. Binary Search(二分查找)](https://leetcode.com/problems/binary-search/)||||\n" +
                "| [20210312](./202103/20210312.md) |[701. Insert into a Binary Search Tree(二叉搜索树的插入)](https://leetcode.com/problems/insert-into-a-binary-search-tree/)||||\n" +
                "| [20210311](./202103/20210311.md) |[1155. Number of Dice Rolls With Target Sum(掷骰子的N种方法-DP)](https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/)||||\n" +
                "| [20210310](./202103/20210310.md) |[739. Daily Temperatures(每日温度)](https://leetcode.com/problems/daily-temperatures/)||||\n" +
                "| [20210309](./202103/20210309.md) |[328. Odd Even Linked List(奇偶链表)](https://leetcode.com/problems/odd-even-linked-list/)||||\n" +
                "| [20210308](./202103/20210308.md) |[150. Evaluate Reverse Polish Notation(逆波兰表达式求值)](https://leetcode.com/problems/evaluate-reverse-polish-notation/)||||\n" +
                "| [20210307](./202103/20210307.md) |[84. Largest Rectangle in Histogram(柱状图最大矩形面积)](https://leetcode.com/problems/largest-rectangle-in-histogram/)||||\n" +
                "| [20210306](./202103/20210306.md) |[877. Stone Game(拿石头)](https://leetcode.com/problems/stone-game/)||||\n" +
                "| [20210305](./202103/20210305.md) |[46. Permutations(全排列)](https://leetcode.com/problems/permutations/)||||\n" +
                "| [20210304](./202103/20210304.md) |[72. Edit Distance(编辑距离)](https://leetcode.com/problems/edit-distance/)||||\n" +
                "| [20210303](./202103/20210303.md) |[102. Binary Tree Level Order Traversal(二叉树层次遍历)](https://leetcode.com/problems/binary-tree-level-order-traversal/)||||\n" +
                "| [20210302](./202103/20210302.md) |[1. Two Sum(两数之和)](https://leetcode.com/problems/two-sum/)||||\n" +
                "| [20210301](./202103/20210301.md) |[119. Pascal's Triangle II(杨辉三角2)](https://leetcode.com/problems/pascals-triangle-ii/)||||";

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
