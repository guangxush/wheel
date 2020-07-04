package logger;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class Logger {
    /**
     * empty()方法同时被 "@Deprecated" 和 "@LoggerAnnotation(value={"a","b"})"所标注
     * (01) @Deprecated，意味着empty()方法，不再被建议使用
     * (02) @LoggerAnnotation, 意味着empty() 方法对应的LoggerAnnotation的value值是默认值"unknown"
     */
    @LoggerAnnotation
    @Deprecated
    public void empty(){
        System.out.println("\nempty");
    }

    /**
     * printLogger() 被 @LoggerAnnotation(value={"test","method"}) 所标注，
     * @LoggerAnnotation(value={"test","method"}), 意味着MyAnnotation的value值是{"test","method"}
     */
    @LoggerAnnotation(value={"test","method"})
    public void printLogger(String clzName, int paramsCount){
        System.out.println("\nclassName: "+clzName+", params count: "+paramsCount);
    }
}
