import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: guangxush
 * @create: 2019/04/07
 */
public class JSONImpl implements JSON {
    /**
     * object -> String
     *
     * @param objects
     * @return
     */
    @Override
    public String stringfy(Object[] objects) {
        StringBuilder sb = new StringBuilder();
        if(objects == null){
            return "";
        }
        if(objects.length>1){
            sb.append("{");
        }
        for(Object object: objects){
            Map<String, Object> map = getKeyAndValue(object);
            sb.append("{");
            Iterator entries = map.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String)entry.getKey();
                Object value = entry.getValue();
                sb.append("\"");
                sb.append(key);
                sb.append("\"");
                sb.append(":");
                if(value!=null && value.getClass().isArray()){
                    //todo 改为其他类型
                    int[] arr = (int[]) value;
                    sb.append(Arrays.toString(arr));
                }else if (value instanceof String){
                    sb.append("\"");
                    sb.append(value);
                    sb.append("\"");
                }else{
                    String mapTemp = value.toString();
                    mapTemp = mapTemp.replace('=',':');
                    sb.append(mapTemp);
                }
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("}");
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        if(objects.length>1){
            sb.append("}");
        }
        return sb.toString();
    }

    private Map<String, Object> getKeyAndValue(Object object){
        Map<String, Object> map = new HashMap<>();
        Class cla = object.getClass();
        Field[] fs = cla.getDeclaredFields();
        for(int i=0;i<fs.length;i++){
            Field f = fs[i];
            f.setAccessible(true);
            Object val;
            try{
                val = f.get(object);
                map.put(f.getName(), val);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * String -> Object
     * 使用栈实现
     *
     * @param str
     * @return
     */
    @Override
    public Map<String, Object> parse(String str) {
        str = removeSpace(str);
        char[] cArr = str.toCharArray();
        int length = cArr.length;
        Stack<Character> stack1 = new Stack<>();
        Stack<String> stack2 = new Stack<>();
        Map<String, Object> map = new HashMap<>(length);
        int i = 0;
        while (i < length) {
            if (cArr[i] == ':') {
                StringBuilder sb = new StringBuilder();
                while (!(stack1.peek() == '{' || stack1.peek() == ',')) {
                    sb.append(stack1.pop());
                }
                if(stack1.peek() == ','){
                    stack1.pop();
                }
                stack1.push(':');
                stack2.push(sb.reverse().toString());
                //map
                if(i+1<length&&cArr[i+1]=='{'){
                    sb = new StringBuilder();
                    while (cArr[i] != '}') {
                        i++;
                        sb.append(cArr[i]);
                    }
                    String key = stack2.pop();
                    key = key.substring(1, key.length() - 1);
                    Map<String, Object> subMap = parse(sb.toString());
                    map.put(key, subMap);
                    if(cArr[i+1] == ','){
                        i++;
                    }
                    stack1.pop();
                }
            } else if (cArr[i] == ',' || cArr[i] == '}') {
                StringBuilder sb = new StringBuilder();
                if(stack2.isEmpty()){
                    break;
                }
                while (stack1.peek() != ':') {
                    sb.append(stack1.pop());
                }
                stack1.pop();
                stack1.push(cArr[i]);
                String key = stack2.pop();
                if (key.contains("\"")) {
                    key = key.substring(1, key.length() - 1);
                }
                String top = sb.reverse().toString();
                if ("true".equals(top)) {
                    map.put(key, true);
                } else if ("false".equals(top)) {
                    map.put(key, false);
                } else if ("null".equals(top)) {
                    map.put(key, null);
                } else {
                    char[] chars = top.toCharArray();
                    if (chars[0] == '[') {
                        //数组
                        Object[] objs = parseArray(top);
                        map.put(key, Arrays.toString(objs));
                    } else if ('0' <= chars[0] && '9' >= chars[0]) {
                        //数字
                        top = top.replaceAll(",", "");
                        try {
                            if (top.contains("e")) {
                                map.put(key, parseBigDecimal(top));
                            } else if (top.contains(".")) {
                                map.put(key, parseDouble(top));
                            } else {
                                map.put(key, parseInteger(top));
                            }
                        } catch (NumberFormatException e) {
                            map.put(key, top);
                        }
                    } else if ('\"' == chars[0]) {
                        //字符串
                        map.put(key, top.substring(1, top.length() - 1));
                    } else {
                        //对象
                        map.put(key, top);
                    }
                }
            } else if (cArr[i] == '[') {
                stack1.push('[');
                while (cArr[i] != ']') {
                    i++;
                    stack1.push(cArr[i]);
                }
            } else {
                stack1.push(cArr[i]);
            }
            i++;
        }
        return map;
    }

    /**
     * 解析数组
     * @param str
     * @return
     */
    private Object[] parseArray(String str) {
        str = str.substring(1, str.length() - 1);
        String[] strArray = str.split(",");
        Object[] objs = new Object[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
            //转换成不同的类型
            //todo 需要继续完善
            //objs[i] = parse(strArray[i]);
            objs[i] = i;
        }
        return objs;
    }

    /**
     * String -> double
     * @param str
     * @return
     */
    private double parseDouble(String str) {
        return Double.parseDouble(str);
    }

    /**
     * String -> int
     * @param str
     * @return
     */
    private int parseInteger(String str) {
        return Integer.parseInt(str);
    }

    /**
     * String -> Double
     * @param str
     * @return
     */
    private Double parseBigDecimal(String str) {
        BigDecimal bd = new BigDecimal(str);
        return bd.doubleValue();
    }

    /**
     * remove the space
     * @param str
     * @return
     */
    private String removeSpace(String str) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        char currentChar;
        while (index < str.length()) {
            currentChar = str.charAt(index);
            if (currentChar == ' ' || currentChar == '\t' || currentChar == '\n') {

            } else {
                sb = sb.append(currentChar);
            }
            index++;
        }
        return sb.toString();
    }

}
