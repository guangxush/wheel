/**
 * @author: guangxush
 * @create: 2019/04/07
 */
public class JSONImpl implements JSON{
    /**
     * object -> String
     * @param object
     * @return
     */
    @Override
    public String stringfy(Object object) {
        return null;
    }

    @Override
    public Object parse(String str) {
        return null;
    }

    /**
     * String -> Object
     * 递归实现/使用栈实现
     * @param str
     * @return
     */
    public Object parse(String str, int index) {
        char[] json = str.toCharArray();
        int length = json.length;
        //去掉多余的字符
        index = removeSpace(str, index);
        if(index>length){
            return null;
        }
        while(index<length){
            char c = json[index];
            if(c=='{'){

            }else if(c=='['){

            }else if(c=='"'){

            }else if(Character.isDigit(c)){

            }else if(Character.isAlphabetic(c)){

            }else{
                throw new IllegalArgumentException("无法匹配");
            }
        }
        return str;
    }

    private int removeSpace(String str, int index){
        char[] c = str.toCharArray();
        while(index < c.length){
            if(c[index]==' '||c[index]=='\t'||c[index]=='\n'){
                index ++;
            }
        }
        return index;
    }

}
