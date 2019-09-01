### 将数据库中的实体转换成对象

##### 自动扫描标准的SQL语句转换成pojo类

## 什么是ORM？
对象关系映射(Object Relational Mapping)，是一种程序技术，用于实现面向对象编程语言里不同类型系统的数据之间的转换。从效果上说，它其实是创建了一个可在编程语言里使用的“虚拟对象数据库”。

面向对象是从软件工程的基本原则（如耦合、聚合、封装）的基础上发展起来的，而关系数据库则是从数学理论发展而来的，两套理论存在显著的区别。为了解决这个不匹配的现象，对象关系映射技术应运而生。
## 要解决的问题

MySQL可以根据建表语句获得相应的字段和字段类型
```
-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `customer_id` bigint(20) NOT NULL COMMENT '客户id',
  `seller_id` bigint(20) NOT NULL COMMENT ' 卖家id',
  `deliver_id` bigint(20) DEFAULT NULL COMMENT '外卖员id',
  `food_id` bigint(20) NOT NULL COMMENT '食品id',
  `food_comment` varchar(100) DEFAULT NULL COMMENT '对商品的评论内容',
  `seller_comment` varchar(100) DEFAULT NULL COMMENT '对商家的评论内容',
  `deliver_comment` varchar(100) DEFAULT NULL COMMENT '对快递员评论内容',
  `food_score` tinyint(1) DEFAULT '5' COMMENT '商品评分：1,2,3,4,5，数字越大评分越高',
  `deliver_score` tinyint(1) DEFAULT '5' COMMENT '快递员评分：1,2,3,4,5，数字越大评分越高',
  `seller_score` tinyint(1) DEFAULT '5' COMMENT '卖家评分：1,2,3,4,5，数字越大评分越高',
  `comment_date` datetime DEFAULT NULL,
  `named` tinyint(1) DEFAULT '1' COMMENT '是否匿名：0匿名，1不匿名',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '评论删除状态0：未删除，1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评价表';
```
开发时往往需要根据字段的类型得到基本的Pojo类：
```
package files;

import com.shgx.order.enums.StatusEnum;
import com.shgx.order.enums.ScoreEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "comment")
public class Comment {
    /**
     * 主键自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 客户id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * 卖家id
     */
    @Column(name = "seller_id")
    private Long sellerId;

    /**
     * 外卖员id
     */
    @Column(name = "deliver_id")
    private Long deliverId;

    /**
     * 食品id
     */
    @Column(name = "food_id")
    private Long foodId;

    /**
     * 对商品的评论内容
     */
    @Column(name = "food_comment")
    private String foodComment;

    /**
     * 对卖家的评论内容
     */
    @Column(name = "seller_comment")
    private String sellerComment;

    /**
     * 对外卖员的评论内容
     */
    @Column(name = "deliver_comment")
    private String deliverComment;

    /**
     * 商品评分
     */
    @Column(name = "food_score")
    @Convert(converter = ScoreEnum.Converter.class)
    private ScoreEnum foodScore;

    /**
     * 外卖员评分
     */
    @Column(name = "deliver_score")
    @Convert(converter = ScoreEnum.Converter.class)
    private ScoreEnum deliverScore;

    /**
     * 卖家评分
     */
    @Column(name = "seller_score")
    @Convert(converter = ScoreEnum.Converter.class)
    private ScoreEnum sellerScore;

    /**
     * 评分时间
     */
    @Column(name = "comment_date")
    private Date commentDate;

    /**
     * 是否匿名
     */
    @Column(name = "named")
    @Convert(converter = StatusEnum.Converter.class)
    private StatusEnum named;

    /**
     * 是否删除
     */
    @Column(name = "deleted")
    @Convert(converter = StatusEnum.Converter.class)
    private StatusEnum deleted;
}

```

这里想根据数据库建表语句生成一个基本的Pojo类(事例代码比较简单，很多细节没有考虑，仅供参考，欢迎补充完善)

## 具体实现(其实就是操作字符串)
```
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
```
## 实现效果
```
public class Comment{

    /**
     * 评论id
     */
    @Column(name = "id")
    Long id;

    /**
     * 客户id
     */
    @Column(name = "customer_id")
    Long customerId;

    /**
     * 家id
     */
    @Column(name = "seller_id")
    Long sellerId;

    /**
     * 外卖员id
     */
    @Column(name = "deliver_id")
    Long deliverId;

    /**
     * 食品id
     */
    @Column(name = "food_id")
    Long foodId;

    /**
     * 对商品的评论内容
     */
    @Column(name = "food_comment")
    String foodComment;

    /**
     * 对商家的评论内容
     */
    @Column(name = "seller_comment")
    String sellerComment;

    /**
     * 对快递员评论内容
     */
    @Column(name = "deliver_comment")
    String deliverComment;

    /**
     * 商品评分：1,2,3,4,5，数字越大评分越高
     */
    @Column(name = "food_score")
    Integer foodScore;

    /**
     * 快递员评分：1,2,3,4,5，数字越大评分越高
     */
    @Column(name = "deliver_score")
    Integer deliverScore;

    /**
     * 卖家评分：1,2,3,4,5，数字越大评分越高
     */
    @Column(name = "seller_score")
    Integer sellerScore;

    /**
     * UL
     */
    @Column(name = "comment_date")
    String commentDate;

    /**
     * 是否匿名：0匿名，1不匿名
     */
    @Column(name = "named")
    Integer named;

    /**
     * 评论删除状态0：未删除，1已删除
     */
    @Column(name = "deleted")
    Integer deleted;
}
```
## 完整代码
[ORM]([https://github.com/guangxush/wheel/tree/master/ORM](https://github.com/guangxush/wheel/tree/master/ORM)
)
