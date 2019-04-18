import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: guangxush
 * @create: 2019/04/18
 */
public class Main {
    public static void main(String[] args) {
        File file = new File("/files/takeout.sql");
        //Path fpath= Paths.get("../files/takeout.sql");
        List<String> rawData = new ArrayList<>();
        try {
            rawData = Sql2Pojo.readFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> results = Sql2Pojo.convert(rawData);
        Sql2Pojo.writeToFile(results);
    }
}
