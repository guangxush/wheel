### 创建线程的三种方法

| 创建线程 | 使用场景 |
| :-:  | :-:  |
| Thread|单继承，有启动线程能力，单继承局限性大 |
| Runnable|无返回值，无启动线程能力，借助Thread类执行任务|
| Callable|有返回值，无启动线程能力，借助Thread类执行任务 |

### 继承Thread

创建TestThread类
```java
public class TestThread extends Thread{

    @Override
    public void run(){
        System.out.println("Test extends Thread!");
    }
}
```

启动TestThread
```java
public class Main {
    public static void main(String[] args) {
        // 1.1创建TestThread对象
        TestThread testThread = new TestThread();
        // 1.2启动线程
        testThread.start();
    }
}
```

运行结果
```text
Test extends Thread!
```

### 实现Runnable

创建TestRunnable类
```java
public class TestRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("Test implements Runnable!");
    }
}
```

启动ThreadRunnable
```java
public class Main {
    public static void main(String[] args) {
        // 2.1创建TestRunnable对象
        TestRunnable testRunnable = new TestRunnable();
        // 2.2创建线程
        Thread thread = new Thread(testRunnable);
        // 2.3启动线程
        thread.start();  
    }
}
```

运行结果
```text
Test implements Runnable!
```


### 实现Callable

创建TestCallable类
```java
public class TestCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "Test implements Callable<T>!";
    }
}
```

启动TestCallable
```java
public class Main {
    public static void main(String[] args) {
        // 3.1创建TestCallable对象
        TestCallable testCallable = new TestCallable();
        // 3.2创建FutureTask实例
        FutureTask<String> futureTask = new FutureTask<>(testCallable);
        // 3.3创建线程
        Thread threadCall = new Thread(futureTask);
        // 3.4 启动线程
        threadCall.start();
        // 3.5 获取执行结果
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
```

运行结果
```text
Test implements Callable<T>!
```


