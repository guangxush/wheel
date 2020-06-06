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
        Path relative = Paths.get("src/files/takeout.sql");
        File file = relative.toFile();
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
