/**
 * @author: guangxush
 * @create: 2020/10/03
 */
public class ParseDB2Property {
    /**
     * 输入：userId,用户ID
     *
     * 输出：
     *
     *      /**
     *      * 用户ID
     *      \*\/
     *      private String userId
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(parse("userId,用户ID"));
    }

    /**
     * 解析数据库字段
     * @param inputDbColumn 输入格式为 userId,用户ID
     * @return
     */
    private static String parse(String inputDbColumn){
        String[] array = inputDbColumn.split(",");
        return parseNote(array[1]) + "\n" + parseProperty(array[0]);
    }

    /**
     * 生成注释
     * @param comment
     * @return
     */
    private static String parseNote(String comment){
        return String.format("/**\n" +
                " * %s\n" +
                " */", comment);
    }

    /**
     * 生成变量，只针对varchar --> String
     * @param value
     * @return
     */
    private static String parseProperty(String value){
        char[] array = value.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for(int i=0;i<array.length;i++){
            if(array[i]=='_'){
                // 下一位转换成大写字母
                array[i+1] -= 32;
                continue;
            }
            buffer.append(array[i]);
        }
        String property = String.format("private String %s;", buffer.toString());
        return property;
    }
}
