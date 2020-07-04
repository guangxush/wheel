package service;


/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class MyServiceImpl implements MyService{

    @MyServiceAnnotation(value="printLogger")
    @Override
    public String printLogger(){
        System.out.println("Add MyServiceAnnotation Method!");
        return "MyServiceAnnotation Method";
    }

    @Override
    public String printLoggerUnAnnotation(){
        System.out.println("Add MyService without Annotation Method!");
        return "Without MyServiceAnnotation Method";
    }
}
