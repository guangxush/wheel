import cache.ContextHolder;
import service.QueryInfo;
import service.impl.QueryInfoImpl;

/**
 * @author: guangxush
 * @create: 2020/05/24
 */
public class Main {
    private static QueryInfo queryInfo = new QueryInfoImpl();
    public static void main(String[] args) {
        // 初始化上下文
        ContextHolder.clear();
        // 第一次查询从DB中获取
        System.out.println(queryInfo.queryInfoByUid("0789128"));
        // 第二次查询从缓存中获取
        System.out.println(queryInfo.queryInfoByUid("0789128"));
        // 清空缓存，再次查询，从DB中获取
        ContextHolder.clear();
        System.out.println(queryInfo.queryInfoByUid("0789128"));
    }
}
