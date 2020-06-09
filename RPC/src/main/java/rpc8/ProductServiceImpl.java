package rpc8;

import common.IProductService;
import common.Product;

/**
 * 放在服务端的一个方法
 *
 * @author: guangxush
 * @create: 2020/05/03
 */
public class ProductServiceImpl implements IProductService {
    @Override
    public Product findProductById(Integer id) {
        return new Product(id, "Apple");
    }
}
