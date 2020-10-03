/**
 * @author: guangxush
 * @create: 2020/10/03
 */
public class ParseVariable2Constant {
    /**
     * 输入：变量命名 helloWorldProperty
     * 输出：常量命名 HELLO_WORLD_PROPERTY
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(variable2Constant("helloWorldProperty"));
    }

    /**
     * 变量转化成常量
     * @param param
     * @return
     */
    private static String variable2Constant(String param){
        char[] array = param.toCharArray();
        String result = "";
        for(int i=0;i<array.length;i++){
            result += array[i];
            // 防止数组越界
            if(i+1==array.length){
                result = result.toUpperCase();
                break;
            }
            // 遇到下一个为大写字母前面要加一个_
            if('A'<=array[i+1] && array[i+1]<='Z'){
                result += "_";
                result = result.toUpperCase();
            }
        }
        return result;
    }
}
