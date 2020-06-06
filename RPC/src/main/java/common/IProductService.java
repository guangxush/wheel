package common;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public interface IProductService {
    /**
     * query product info
     * @param id
     * @return
     */
    Product findProductById(Integer id);
}
