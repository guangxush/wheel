/**
 * @author: guangxush
 * @create: 2019/08/24
 */
@FunctionalInterface
public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
