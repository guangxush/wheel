## 使用ThreadLocal做查询缓存

ThreadLocal是一个本地线程副本变量工具类。主要用于将私有线程和该线程存放的副本对象做一个映射，各个线程之间的变量互不干扰。

在高并发场景下，可以实现无状态的调用，特别适用于各个线程依赖不通的变量值完成操作的场景。 

对于不同的线程，每次获取副本值时，别的线程并不能获取到当前线程的副本值，形成了副本的隔离，互不干扰。

### 案例

本案例采用ThreadLocal<Context> threadLocal对查询对象进行缓存，减轻DB压力

```java
public class ContextHolder {

    /** 本地线程 */
    private static ThreadLocal<Context> threadLocal = new ThreadLocal<Context>();

    public static ThreadLocal<Context> get() {
        return threadLocal;
    }

    public static void set(ThreadLocal<Context> threadLocal) {
        ContextHolder.threadLocal = threadLocal;
    }

    public static void clear(){
        try{
            if(null != threadLocal.get()){
                threadLocal.remove();
            }
        }catch (Exception e){
            System.out.println("线程上下文清理失败");
        }finally {
            threadLocal.set(null);
        }
    }

    public static void addUserInfo(String uid, one.Student student){
        if(StringUtils.isBlank(uid) || student ==null){
            System.out.println("uid和student不能为空");
            return;
        }
        Context context = threadLocal.get();
        if(null == context){
            context = new Context();
            threadLocal.set(context);
        }
        context.putObject(uid, student);
    }

    public static one.Student getStudent(String uid){
        if(StringUtils.isBlank(uid)){
            System.out.println("uid不能为空");
            return null;
        }
        Context context = threadLocal.get();
        if(null == context){
            return null;
        }
        return (one.Student) context.getObject(uid);
    }
}
```

其中缓存的存储与查询如下：

```java
public class QueryInfoImpl implements QueryInfo {

    public one.Student queryInfoByUid(String uid) {
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
        one.Student student = StudentRepository.getStudentById(uid);

        //保存结果到缓存中
        ContextHolder.addUserInfo(uid, student);
        return student;
    }
}
```

Main方法进行调用
```java
public class Client {
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
```

### 输出结果

```text
*******调用DB*******
Student{uid='0789128', name='shgx', age=22}
*******调用缓存*******
Student{uid='0789128', name='shgx', age=22}
*******调用DB*******
Student{uid='0789128', name='shgx', age=22}
```

### 拓展

当然缓存的形式有很多种，尤其Spring这种应用，用到缓存的地方会很多，这里可以参考Guava缓存进行实践：

https://github.com/guangxush/SpringBoot_GuavaCache