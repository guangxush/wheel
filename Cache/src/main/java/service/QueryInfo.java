package service;

import model.Student;

/**
 * @author: guangxush
 * @create: 2020/05/24
 */
public interface QueryInfo {
    /**
     * 从缓存中查询用户信息
     *
     * @param uid
     * @return
     */
    Student queryInfoByUid(String uid);
}
