### 各种java解析小工具

目前包含的内容有:

- 将变量转换成静态常量值或枚举值
- 将数据库字段解析为变量
- Java使用FileChannel实现大文件读取、写入、复制、倒序复制
- 正则表达式匹配
- 根据年月日生成文件

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

#### Java使用FileChannel实现大文件读取、写入、复制、倒序复制

[方法](./src/main/java/ChannelFileReaderAndWriter.java):
```java
    /**
     * 写文件
     * @param fileName
     * @throws IOException
     */
    public void doWrite(String fileName) throws IOException {
        outFile = new RandomAccessFile(fileName, "rw");
        outChannel = outFile.getChannel();
        String newData = "New String to write to file... \n";
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            outChannel.write(buf);
        }
        outChannel.close();
        System.out.println("Write Over!\n");
    }

    /**
     * 读文件
     * @param fileName
     * @throws IOException
     */
    public void doRead(String fileName) throws IOException {
        inFile = new RandomAccessFile(fileName, "rw");
        inChannel = inFile.getChannel();
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        inFile.close();
    }

    /**
     * 拷贝文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public void doCopy(String sourceFile, String targetFile) throws IOException {
        inFile = new RandomAccessFile(sourceFile, "rw");
        inChannel = inFile.getChannel();
        RandomAccessFile bFile = new RandomAccessFile(targetFile, "rw");
        FileChannel outChannel = bFile.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy over");
        inChannel.close();
    }
    /**
     * 按照行读文件
     * @param fileName
     * @throws IOException
     */
    public void doReadLine(String fileName) throws IOException {
        inFile = new RandomAccessFile(fileName, "rw");
        int lineNum = 0;
        inFile.seek(lineNum);
        String line = "";
        while((line = inFile.readLine())!=null){
            line =  new String(line.getBytes("UTF-8"), "UTF-8");
            System.out.println(line);
        }
        inFile.close();
    }

    /**
     * 倒序拷贝文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public void reverseCopy(String sourceFile, String targetFile) throws IOException {
        inFile = new RandomAccessFile(sourceFile, "r");
        inChannel = inFile.getChannel();
        outFile = new RandomAccessFile(targetFile, "rw");
        outChannel = outFile.getChannel();
        // 文件长度
        long len = inFile.length();
        System.out.println("文件开始指针为"+0);
        //指针是从0到length-1
        long nextEnd =  len - 1;
        String line;
        //seek到最后一个字符
        inFile.seek(nextEnd);
        int c = -1;
        while (nextEnd >= 0) {
            c = inFile.read();
            //只有行与行之间才有\r\n，这表示读到每一行上一行的末尾的\n，而执行完read后，指针指到了这一行的开头字符
            if (c == '\n')
            {
                line =  new String(inFile.readLine().getBytes("UTF-8"), "UTF-8");
                System.out.println(line);
                writeLine(line, outChannel);
            }
            // 当文件指针退至文件开始处，输出第一行
            if (nextEnd == 0) {
                //不需要为下次做准备了，但是因为read()方法指针从0到了1，需重置为0
                inFile.seek(0);
                line =  new String(inFile.readLine().getBytes("UTF-8"), "UTF-8");
                System.out.println(line);
                writeLine(line, outChannel);
            } else {
                //为下一次循环做准备
                inFile.seek(nextEnd-1);
            }
            nextEnd--;
        }
        System.out.println("Reverse Copy over");
        outChannel.close();
        inChannel.close();
    }

    private void writeLine(String line, FileChannel outChannel)throws IOException{
        // 写入文件
        line += "\n";
        buf.clear();
        buf.put(line.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            outChannel.write(buf);
        }
    }
```
文件按照行倒序输出可以使用这个命名
```shell script
perl -e 'print reverse <>' filename
```

#### 正则表达式匹配

[PatternMatch](./src/main/java/PatternMatch.java)
实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。

```java
    private boolean match(char[] str, int i, char[] pattern, int j) {
        if (j == pattern.length) {
            return str.length == i;
        }
        if (j < pattern.length - 1 && pattern[j + 1] == '*') {
            if (str.length != i && (str[i] == pattern[j] || pattern[j] == '.')) {
                return match(str, i, pattern, j + 2) || match(str, i + 1, pattern, j);
            } else {
                return match(str, i, pattern, j + 2);
            }
        }
        if (str.length != i && (str[i] == pattern[j] || pattern[j] == '.')) {
            return match(str, i + 1, pattern, j + 1);
        }
        return false;
    }
```

#### 根据年月日生成文件

[根据年月日生成文件并追加内容](./src/main/java/GenerateFileByDate.java)

为什么要做这件事情，因为[看这里](https://github.com/guangxush/ARTS/tree/master/guangxu)

生成文件关键代码
```java
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
```

写入文本关键代码
```java
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
```
