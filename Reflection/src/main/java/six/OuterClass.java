package six;

import java.util.Date;

/**
 * 父类 外部类
 * @author: guangxush
 * @create: 2020/06/14
 */
public class OuterClass implements Parents{
    private Long id;
    private String name;
    private Integer sex;
    private Double age;
    private Date birthDay;

    public OuterClass() {
    }

    public OuterClass(Long id, String name, Integer sex, Double age, Date birthDay) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birthDay = birthDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public String toString() {
        return "OuterClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthDay=" + birthDay +
                '}';
    }

    @Override
    public void function() {
        System.out.println("OuterClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthDay=" + birthDay +
                '}');
    }
}
