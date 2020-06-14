package six;


import java.util.Date;

/**
 * 子类，内部类
 *
 * @author: guangxush
 * @create: 2020/06/14
 */
public class InnerClass implements Parents {

    private Long id;
    private String name;
    private Integer sex;
    private Double age;
    private Date birthDay;
    // 新增
    private String address;

    public InnerClass(){}

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "InnerClass{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", sex=" + getSex() +
                ", age=" + getAge() +
                ", birthDay=" + getBirthDay() +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public void function() {
        System.out.println("反射实现 InnerClass{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", sex=" + getSex() +
                ", age=" + getAge() +
                ", birthDay=" + getBirthDay() +
                ", address='" + address + '\'' +
                '}');
    }
}
