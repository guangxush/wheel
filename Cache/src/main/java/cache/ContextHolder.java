package cache;

import model.Student;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: guangxush
 * @create: 2020/05/24
 */
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

    public static void addUserInfo(String uid, Student student){
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

    public static Student getStudent(String uid){
        if(StringUtils.isBlank(uid)){
            System.out.println("uid不能为空");
            return null;
        }
        Context context = threadLocal.get();
        if(null == context){
            return null;
        }
        return (Student) context.getObject(uid);
    }
}
