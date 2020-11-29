### 自定义一个线程池

[ThreadPoolService](./src/main/java/ThreadFactory.java)


### 线程池的几种使用

1. 提交异步任务

public interface Future<V>Future 表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并获取计算的结果。计算完成后只能使用 get 方法来获取结果，如有必要，计算完成前可以阻塞此方法。取消则由 cancel 方法来执行。还提供了其他方法，以确定任务是正常完成还是被取消了。一旦计算完成，就不能再取消计算。如果为了可取消性而使用 Future 但又不提供可用的结果，则可以声明 Future<?> 形式类型、并返回 null 作为底层任务的结果。

public interface Callable<V>返回结果并且可能抛出异常的任务。实现者定义了一个不带任何参数的叫做 call 的方法。 

Callable 接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。但是 Runnable 不会返回结果，并且无法抛出经过检查的异常。 

Executors 类包含一些从其他普通形式转换成 Callable 类的实用方法

Callable and Future虽然Runnable 提供了一种包裹要被在一个不同的线程中执行的代码的方式。它有一个缺陷， 不能从执行中返回结果。仅有的一种从一个Runnable 的执行中返回值的方式是把结果赋值给一个在Runnable 外部作用域中可访问的变量。

Callable 在java 5中被引入， 作为Runnable 的一个对等（peer）。 Callable除了有一个Call 方法而不是Run 方法外（和Runnable）本质相同。 Call 方法有其它的能力去返回一个结果并且允许抛出检查时异常。

Callable 任务提交所产生的结果可用后能够被Future 获取，Future 可以被看作一个整理存储Callable 计算结果的容器。callable的计算能持续在另一个线程中， 并且任何获取一个Future 结果的尝试都将被阻塞，并且一旦（结果）变得可用，便会返回结果。

2. 提交同步任务

Runnable有一个run()函数，用于将耗时操作写在其中，该函数没有返回值。然后使用某个线程去执行该runnable即可实现多线程，Thread类在调用start()函数后就是执行的是Runnable的run()函数。


3. 二者区别

Callable 和 Future接口的区别

- Callable规定的方法是call()，而Runnable规定的方法是run(). 
- Callable的任务执行后可返回值，而Runnable的任务是不能返回值的。  
- call()方法可抛出异常，而run()方法是不能抛出异常的。 
- 运行Callable任务可拿到一个Future对象， Future表示异步计算的结果。 
- 它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。 
- 通过Future对象可了解任务执行情况，可取消任务的执行，还可获取任务执行的结果。 
- Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其它线程执行的任务。

4. 使用案例

[submitTask](./src/main/java/submittask/MainTask.java)





