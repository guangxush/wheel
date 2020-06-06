## 反射
反射是Java语言的重要特性，它允许程序运行时进行自我检查，也允许对内部的成员
进行操作，能够实现在运行时对类进行装载，使程序运行时更加灵活，但是也有注意正确使用
否则会对性能有影响。

### 案例1 基本的反射

父类
```java
public class Parents {
    public void function(){
        System.out.println("I'm parents!");
    }
}

```

子类
```java
public class Children extends Parents{
    @Override
    public void function() {
        System.out.println("I'm children!");;
    }
}
```

测试类
```java
import one.Parents;public class TestReflection {
    @org.junit.Test
    public void testFunction() throws Exception{
        Class cls = Class.forName("one.Children");
        Parents parents = (Parents)cls.newInstance();
        parents.function();
    }
}
```

这时候会调用子类的function方法输出
```text
I'm children!
```

所以我们可以写一个父类的工厂方法，传入子类的名称，自动调用子类的方法

一些RPC框架就是基于这种动态代理的思想

这里我们增加一个实现类Student
```java
public class Student extends Parents{
    @Override
    public void function() {
        System.out.println("I'm children!");;
    }
}
```

增加一个工厂，模拟注册中心，调用子类的方法
```java
public class Factory {
    private static Map<String, String> map = new HashMap<String, String>();

    static{
        map.clear();
        // 模拟注册中心，存放实现类
        map.put("one.Children", "one.Children");
        map.put("one.Student", "one.Student");
    }

    public static void main(String[] args) {
        run("one.Student");
    }

    private static
    void run(String clzName){
        if(null==clzName || clzName.length()==0 || "".equals(clzName)){
            System.out.println("参数不能为空");
            return;
        }
        if(!map.containsKey(clzName)){
            System.out.println("参数不合法");
            return;
        }
        try{
            Class cls = Class.forName(map.get(clzName));
            Parents parents = (Parents)cls.newInstance();
            System.out.println("执行方法"+clzName);
            parents.function();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
```
运行Factory会出现
```text
执行方法Student
I'm children!
```
