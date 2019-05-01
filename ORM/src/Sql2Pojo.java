import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guangxush
 * @create: 2019/04/18
 */
public class Sql2Pojo {

    /**
     * 按行读文件
     * @param fin
     * @throws IOException
     * @return
     */
    public static List<String> readFile(File fin) throws IOException {
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line = null;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    /**
     * 按行匹配得到想要的结果
     * @param line
     * @return
     */
    public static String match(String line){
        String[] words = line.trim().split(" ");
        String result = null;
        switch (words[0]){
            case "--":
            case "DROP": {
                break;
            }
            case "CREATE":{
                result = words[2].substring(1,words[2].length()-1);
                //首字母大写
                result = result.substring(0,1).toUpperCase()+result.substring(1);
                result = "public class "+ result + "{\n";
                break;
            }
            default:{
                String lastWord = words[words.length-1];
                /**
                 * 主键自增id
                 */
                String notes = "\n    /**\n     * "+ lastWord.substring(1, lastWord.length()-2)+"\n     */";
                char lastChar = lastWord.charAt(lastWord.length()-1);
                switch (lastChar){
                    case ',':{
                        //转换为对象的属性
                        //@Column(name = "customer_id")
                        String temp = words[0].substring(1, words[0].length()-1);
                        String annotate = "\n    @Column(name = \""+temp+"\")\n";
                        String[] items = temp.split("_");
                        int index = 0;
                        for(String item:items){
                            if(index==0){
                                result = item;
                                index ++;
                                continue;
                            }
                            item = item.substring(0,1).toUpperCase()+item.substring(1);
                            result = result+item;
                        }
                        //转换属性的类型
                        String qualifiers = words[1];
                        result = notes+annotate + "    " + recQualifiers(qualifiers)+" "+result+";\n";
                        break;
                    }default:{
                        result = null;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 修饰符判断
     * @param qualifiers
     * @return
     */
    private static String recQualifiers(String qualifiers){
        String result = null;

        if(qualifiers==null){
            return result;
        }
        int index = (qualifiers.indexOf('(')!=-1?qualifiers.indexOf('('):qualifiers.length()-1);
        qualifiers = qualifiers.substring(0, index);
        switch (qualifiers){
            case "bigint":{
                result = "Long";
                break;
            }case "varchar":{
                result = "String";
                break;
            }case "tinyint":{
                result = "Integer";
                break;
            }case "datetime":{
                result = "Date";
                break;
            }
            default:{
               result = "String";
            }
        }
        return result;
    }


    /**
     * 转换语句
     * @param lines
     * @return
     */
    public static List<String> convert(List<String> lines){
        List<String> results = new ArrayList<>();
        for(String line:lines){
            String result = match(line);
            if(result!=null){
                results.add(result);
            }
        }
        return results;
    }

    /**
     * 把实验结果写入到文件中
     * @param result
     */
    public static void writeToFile(List<String> result){
        Path fpath= Paths.get("src/files/comment_result.txt");
        //创建文件
        if(!Files.exists(fpath)) {
            try {
                Files.createFile(fpath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //创建BufferedWriter
        try {
            BufferedWriter bfw=Files.newBufferedWriter(fpath);
            int count = result.size();
            for(String line : result){
                bfw.write(line);
                if(--count==0){
                    bfw.write("}");
                }
            }
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
