package repository;

import model.Student;

/**
 * 模拟数据库查询
 * @author: guangxush
 * @create: 2020/05/24
 */
public class StudentRepository {
    public static Student getStudentById(String uid){
        System.out.println("*******调用DB*******");
        return new Student("0789128","shgx", 22);
    }
}
