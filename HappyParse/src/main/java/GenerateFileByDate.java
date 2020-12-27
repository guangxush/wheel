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
        prefix = 20210101;
        while (prefix <= 20210131) {
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
