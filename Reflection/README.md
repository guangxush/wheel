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

### 案例2 动态代理实现反射

父类接口
```java
public interface Parents {
    public void function();
}
```

子类实现
```java
public class Children implements Parents{
    @Override
    public void function() {
        System.out.println("I'm children!");
    }
}
```

基本的代理类，继承InvocationHandler接口，实现invoke方法。
```Java
public class Intermediary implements InvocationHandler {
    private Object post;
    public Intermediary(Object post) {
        this.post = post;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(post, args);
        System.out.println("代理类：打印日志");
        return invoke;
    }
}
```

优雅的代理类，直接返回对象。采用内部类的方式，将反射的方法当作参数传递给InvocationHandler

```java
public class GetObject {
    /**
     * 反射+动态代理的方式调用Parents里面的方法
     * @return
     */
    public static void runObject(final Parents post){
        // 调用方法时的处理器，本质都是在调用invoke()方法
        InvocationHandler h = new InvocationHandler() {
            /**
             * 调用方法的处理内容放在invoke里面
             * @param proxy 代理对象
             * @param method 调用的方法
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(post, args);
                System.out.println("代理类：打印日志");
                return invoke;
            }
        };
        // 参数：产生这个代理类的classLoader, 实现了这个代理类的接口，h
        Object o = Proxy.newProxyInstance(Parents.class.getClassLoader(), new Class[]{Parents.class}, h);
        System.out.println(o.getClass().getName());
        System.out.println(o.getClass().getInterfaces()[0]);
        Parents parents = (Parents) o;
        parents.function();
        return;
    }
}
```

测试下上述两个类的效果
```java
public class TestIntermediary {
    public static void main(String[] args) {

        // 基本的代理类
        Parents child = new Children();
        Intermediary intermediary = new Intermediary(child);
        Parents proxy = (Parents) Proxy.newProxyInstance(child.getClass().getClassLoader(), child.getClass().getInterfaces(), intermediary);
        proxy.function();

        // 优雅的代理工具类
        GetObject.runObject(new Children());
    }
}
```

通过代理类可以在代理类中包装一些方法进去，以下是运行结果

```
I'm children!
代理类：打印日志
com.sun.proxy.$Proxy0
interface two.Parents
I'm children!
代理类：打印日志
```

### 案例3 通过代理类设置方法拦截器

自定义一个服务
```java
public class MyService {
    public String run(String something){
        // int i = 1/0;
        return "服务正在运行...." + something;
    }
}
```

定义一个其他服务
```java
public class OtherService {
    public String runOther(String something){
        // int i = 1/0;
        return "服务正在运行...." + something;
    }
}
```

写一个服务拦截器，对不是“run”的方法进行拦截
```java
public class ServiceInterceptor implements MethodInterceptor {

    private static final String INTERCEPTOR = "run";

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        if(!INTERCEPTOR.equals(methodInvocation.getMethod().getName())){
            System.out.println("不能执行该方法" + methodInvocation.getMethod().getName());
            return null;
        }
        try {
            System.out.println("方法执行之前：" + methodInvocation.getMethod().toString());

            result = methodInvocation.proceed();
            System.out.println("方法正常运行结果：" + result);

            System.out.println("方法执行之后：" + methodInvocation.getMethod().toString());
            return result;

        } catch (Exception e) {
            System.out.println("方法出现异常:" + e.toString());
            System.out.println("方法运行Exception结果：" + result);
            return result;
        }
    }
}
```

```java
public class TestMethodInterceptor {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new MyService());
        proxyFactory.addAdvice(new ServiceInterceptor());

        Object proxy = proxyFactory.getProxy();
        MyService myService = (MyService) proxy;
        myService.run("通过代理工厂设置代理对象，拦截代理方法");

        proxyFactory.setTarget(new OtherService());
        proxy = proxyFactory.getProxy();
        OtherService otherService = (OtherService) proxy;
        otherService.runOther("通过代理工厂设置代理对象，拦截代理方法");
    }
}
```

可以看到otherService.runOther方法被拦截了
```java
方法执行之前：public java.lang.String three.MyService.run(java.lang.String)
方法正常运行结果：服务正在运行....通过代理工厂设置代理对象，拦截代理方法
方法执行之后：public java.lang.String three.MyService.run(java.lang.String)
不能执行该方法runOther
```

### 案例4 抽象服务的接口

定义一个父类实现接口打印
```java
public class BaseService {
    /**
     *
     * @param something
     * @return
     */
    String run(String something){
        return null;
    }
    void printLog(){
        System.out.println("统一打印日志");
    }
}
```

ServiceOne实现上述接口

```java
public class ServiceOne extends BaseService {
    @Override
    String run(String something) {
        return "ServiceOne "+ something;
    }
}
```

ServiceTwo实现上述接口

```java
public class ServiceTwo extends BaseService{

    @Override
    String run(String something) {
        return "ServiceTwo "+ something;
    }
}
```

借助Spring拦截器，实现反射
```java
public class ServiceInterceptor implements MethodInterceptor {

    private static final String INTERCEPTOR = "printLog";

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        try {
            if(INTERCEPTOR.equals(methodInvocation.getMethod().getName())){
                //日志直接打印
                result = methodInvocation.proceed();
                return result;
            }

            System.out.println("方法执行之前：" + methodInvocation.getMethod().toString());

            result = methodInvocation.proceed();
            System.out.println("方法正常运行结果：" + result);

            System.out.println("方法执行之后：" + methodInvocation.getMethod().toString());
            return result;

        } catch (Exception e) {
            System.out.println("方法出现异常:" + e.toString());
            System.out.println("方法运行Exception结果：" + result);
            return result;
        }
    }
}
```

定义测试类，调用ServiceOne和ServiceTwo, 把二者的公共方法printLog直接放在test里面调用，反射不会对其执行环绕方法。
```java
public class TestMethodInterceptor {
    public static void main(String[] args) {
        ServiceOne serviceOne = (ServiceOne) test(new ServiceOne());
        serviceOne.run("通过代理工厂设置代理对象，拦截代理方法");

        ServiceTwo serviceTwo = (ServiceTwo) test(new ServiceTwo());
        serviceTwo.run("通过代理工厂设置代理对象，拦截代理方法");
    }

    public static BaseService test(BaseService baseService){
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(baseService);
        proxyFactory.addAdvice(new ServiceInterceptor());

        Object proxy = proxyFactory.getProxy();
        BaseService baseProxy = (BaseService) proxy;
        baseProxy.printLog();
        return baseProxy;
    }
}
```

查看执行结果
```
统一打印日志
方法执行之前：java.lang.String four.ServiceOne.run(java.lang.String)
方法正常运行结果：ServiceOne 通过代理工厂设置代理对象，拦截代理方法
方法执行之后：java.lang.String four.ServiceOne.run(java.lang.String)
统一打印日志
方法执行之前：java.lang.String four.ServiceTwo.run(java.lang.String)
方法正常运行结果：ServiceTwo 通过代理工厂设置代理对象，拦截代理方法
方法执行之后：java.lang.String four.ServiceTwo.run(java.lang.String)
```


### 案例5 使用FastClass完成反射，执行类中的所有方法

父接口与原来保持一致

```java
public interface Parents {
    String hello(String name);
}
```
子类实现接口

```java
public class Children implements Parents {

    @Override
    public String hello(String name) {
        return "I'm " + name;
    }
}
```

实现基本的代理类
```java
public class Intermediary implements InvocationHandler {

    private Object post;

    public Intermediary(Object post) {
        this.post = post;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(post, args);
        System.out.println("代理类：打印日志");
        return invoke;
    }
}
```
调用FastClass实现反射，循环遍历类中的方法执行

```java
public class GetObject {

    /**
     * 反射+动态代理的方式调用Parents里面的方法
     * @return
     */
    public static Object runObject(final Parents post, String methodName, Object[] parameters){
        try {
            Class<?> clazz = Class.forName(post.getClass().getName());
            //获取本类的所有方法，存放入数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if(methodName.equals(method.getName())){
                    System.out.println("方法名："+method.getName());
                    //获取本方法所有参数类型，存入数组
                    Class<?>[] getTypeParameters = method.getParameterTypes();
                    if(getTypeParameters.length==0){
                        System.out.println("此方法无参数");
                    }
                    for (Class<?> class1 : getTypeParameters) {
                        String parameterName = class1.getName();
                        System.out.println("参数类型："+parameterName);
                    }
                    FastClass fastClazz = FastClass.create(Parents.class);
                    int methodIndex = fastClazz.getIndex(method.getName(), getTypeParameters);
                    return fastClazz.invoke(methodIndex, post, parameters);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

执行代理类
```java
public class TestIntermediary {
    public static void main(String[] args) {

        // 基本的代理类
        Parents child = new Children();
        Intermediary intermediary = new Intermediary(child);
        Parents proxy = (Parents) Proxy.newProxyInstance(child.getClass().getClassLoader(), child.getClass().getInterfaces(), intermediary);
        System.out.println(proxy.hello("shgx"));

        // 优雅的代理工具类
        System.out.println(GetObject.runObject(new Children(), "hello", new String[]{"shgx"}));
    }
}
```
执行结果如下：
```
代理类：打印日志
I'm shgx
方法名：hello
参数类型：java.lang.String
I'm shgx
```

### 案例6 使用PropertyUtil和BeanUtil拷贝参数

父类接口
```java
public interface Parents {
    void function();
}
```

子类-外部类
```java
public class OuterClass implements Parents{
    private Long id;
    private String name;
    private Integer sex;
    private Double age;
    private Date birthDay;
    // 省略get set toString方法

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
```
子类-内部类
```java
public class InnerClass implements Parents {
    private Long id;
    private String name;
    private Integer sex;
    private Double age;
    private Date birthDay;
    // 新增, 与上面的类形成对比
    private String address;
    // 省略get set toString方法

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
```

优雅的代理类实现反射
```java
public class GetObject {

    /**
     * 反射+动态代理的方式调用Parents里面的方法
     * @return
     */
    public static void runObject(final Parents post){
        // 调用方法时的处理器，本质都是在调用invoke()方法
        InvocationHandler h = new InvocationHandler() {
            /**
             * 调用方法的处理内容放在invoke里面
             * @param proxy 代理对象
             * @param method 调用的方法
             * @param args 传递的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object invoke = method.invoke(post, args);
                return invoke;
            }
        };
        // 参数：产生这个代理类的classLoader, 实现了这个代理类的接口，h
        Object o = Proxy.newProxyInstance(Parents.class.getClassLoader(), new Class[]{Parents.class}, h);
        Parents parents = (Parents) o;
        parents.function();
    }
}
```

实现对象之间的参数拷贝
```java
public class CopyUtil {

    @SuppressWarnings("unchecked")
    public static void getClassByBeanUtil(InnerClass dest, OuterClass source) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("BeanUtils result:" + dest);
    }

    public static void getClassByPropertyUtil(InnerClass dest, OuterClass source) {
        try {
            PropertyUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("PropertyUtils result:" + dest);
    }
}
```


```java
public class TestIntermediary {
    public static void main(String[] args) {

        // 基本的代理类
        OuterClass outerClass = new OuterClass(1L, "shgx", 1, 23.5, new Date());
        // BeanUtil 完成对象拷贝
        InnerClass innerClassOne = new InnerClass();
        CopyUtil.getClassByBeanUtil(innerClassOne, outerClass);
        GetObject.runObject(innerClassOne);

        // PropertyUtil 完成对象拷贝
        InnerClass innerClassTwo = new InnerClass();
        CopyUtil.getClassByPropertyUtil(innerClassTwo, outerClass);
        GetObject.runObject(innerClassTwo);

        // 置空
        outerClass.setId(null);
        outerClass.setName(null);
        outerClass.setAge(null);
        outerClass.setSex(null);
        outerClass.setBirthDay(null);
        // BeanUtil
        CopyUtil.getClassByBeanUtil(innerClassOne, outerClass);
        GetObject.runObject(innerClassOne);

        // PropertyUtil
        CopyUtil.getClassByPropertyUtil(innerClassTwo, outerClass);
        GetObject.runObject(innerClassTwo);

    }
}
```

执行结果
```
BeanUtils result:InnerClass{id=1, name='shgx', sex=1, age=23.5, birthDay=Sat Jul 04 18:23:51 CST 2020, address='null'}
反射实现 InnerClass{id=1, name='shgx', sex=1, age=23.5, birthDay=Sat Jul 04 18:23:51 CST 2020, address='null'}
PropertyUtils result:InnerClass{id=1, name='shgx', sex=1, age=23.5, birthDay=Sat Jul 04 18:23:51 CST 2020, address='null'}
反射实现 InnerClass{id=1, name='shgx', sex=1, age=23.5, birthDay=Sat Jul 04 18:23:51 CST 2020, address='null'}
BeanUtils result:InnerClass{id=null, name='null', sex=null, age=null, birthDay=null, address='null'}
反射实现 InnerClass{id=null, name='null', sex=null, age=null, birthDay=null, address='null'}
PropertyUtils result:InnerClass{id=null, name='null', sex=null, age=null, birthDay=null, address='null'}
反射实现 InnerClass{id=null, name='null', sex=null, age=null, birthDay=null, address='null'}
```
上述方法可以在不同系统之间完成参数传输，实现RPC调用

置空之后在执行发现对象拷贝完成也是null，不会赋予默认值

多余的参数会赋予null值，但是不会删除

### 案例7 使用注解和反射实现日志打印

使用注解的方式配置日志
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogAnnotation {
    /** 日志类型 */
    LogTypeEnum logType() default LogTypeEnum.SERVICE_LOG;

    /** 业务名 */
    String bizName() default "";

    /** 自定义日志打印*/
    Class<? extends LogInfo> customerLogType() default LogInfo.class;

    /** 是否打印日志*/
    boolean recordMonitorData() default false;
}
```
不在展开描述
