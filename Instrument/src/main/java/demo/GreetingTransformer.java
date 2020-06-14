package demo;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Date;

/**
 * @author: guangxush
 * @create: 2020/06/14
 */
public class GreetingTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if ("demo/Dog".equals(className)) {
            System.out.println("Dog's method invoke at\t" + new Date());
        }
        return null;    }
}
