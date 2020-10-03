### 各种java解析小工具

#### 将变量转换成静态常量值或枚举值

输入：
```text
helloWorldProperty
```

输出：
```text
HELLO_WORLD_PROPERTY
```

[方法](./src/main/java/ParseVariable2Constant.java):
```java
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
```

#### 将数据库字段解析为变量

输入：
```text
userId,用户ID
```

输出：
```text
/**
 * 用户ID
 */
private String userId;
```

[方法](./src/main/java/ParseDB2Property.java):
```java
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
```