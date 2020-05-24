package service.impl;

import cache.ContextHolder;
import model.Student;
import org.apache.commons.lang3.StringUtils;
import repository.StudentRepository;
import service.QueryInfo;

/**
 * @author: guangxush
 * @create: 2020/05/24
 */
public class QueryInfoImpl implements QueryInfo {

    public Student queryInfoByUid(String uid) {
        //判空
        if(StringUtils.isBlank(uid)){
            return null;
        }
        //缓存中查询
        if(null != ContextHolder.getStudent(uid)){
            System.out.println("*******调用缓存*******");
            return ContextHolder.getStudent(uid);
        }
        //缓存中不存在，去DB中查询
        Student student = StudentRepository.getStudentById(uid);

        //保存结果到缓存中
        ContextHolder.addUserInfo(uid, student);
        return student;
    }
}
