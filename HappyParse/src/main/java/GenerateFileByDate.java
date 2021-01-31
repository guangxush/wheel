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
        GenerateFileByDate date = new GenerateFileByDate();
        long prefix = Long.valueOf(getTodayYearMonthDay());
        System.out.println(prefix);
        prefix = 20210201;
        while (prefix <= 20210228) {
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
        String before = "| [20210131](./202101/20210131.md) |[145. Binary Tree Postorder Traversal(二叉树后序遍历)](https://leetcode.com/problems/binary-tree-postorder-traversal/)||||\n" +
                "| [20210130](./202101/20210130.md) |[144. Binary Tree Preorder Traversal(二叉树前序遍历)](https://leetcode.com/problems/binary-tree-preorder-traversal/)||||\n" +
                "| [20210129](./202101/20210129.md) |[94. Binary Tree Inorder Traversal(二叉树中序遍历)](https://leetcode.com/problems/binary-tree-inorder-traversal/)||||\n" +
                "| [20210128](./202101/20210128.md) |[91. Decode Ways(动态规划解码方法)](https://leetcode.com/problems/decode-ways/)||||\n" +
                "| [20210127](./202101/20210127.md) |[48. Rotate Image(图像旋转)](https://leetcode.com/problems/rotate-image)||||\n" +
                "| [20210126](./202101/20210126.md) |[215. Kth Largest Element in an Array(数组中第K个最大元素)](https://leetcode.com/problems/kth-largest-element-in-an-array/)||||\n" +
                "| [20210125](./202101/20210125.md) |[887. Super Egg Drop（扔鸡蛋）](https://leetcode.com/problems/super-egg-drop/)||||\n" +
                "| [20210124](./202101/20210124.md) |[206. Reverse Linked List（链表反转）](https://leetcode.com/problems/reverse-linked-list/)||||\n" +
                "| [20210123](./202101/20210123.md) |[1095. Find in Mountain Array（查找山峰数组中的元素）](https://leetcode.com/problems/find-in-mountain-array/)||||\n" +
                "| [20210122](./202101/20210122.md) |[287. Find the Duplicate Number（发现重复元素）](https://leetcode.com/problems/find-the-duplicate-number/)||||\n" +
                "| [20210121](./202101/20210121.md) |[148. Sort List（链表排序）](https://leetcode.com/problems/sort-list/)||||\n" +
                "| [20210120](./202101/20210120.md) |[912. Sort an Array(数组排序)](https://leetcode.com/problems/sort-an-array/)||||\n" +
                "| [20210119](./202101/20210119.md) |[168. Excel Sheet Column Title(Excel转换)](https://leetcode.com/problems/excel-sheet-column-title/)||||\n" +
                "| [20210118](./202101/20210118.md) |[141. Linked List Cycle(判断单链表是否有环)](https://leetcode.com/problems/linked-list-cycle/)|不用中间变量交换两个数组的值|||\n" +
                "| [20210117](./202101/20210117.md) |[25. Reverse Nodes in k-Group(反转第K组链表)](https://leetcode.com/problems/reverse-nodes-in-k-group/)||||";

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
