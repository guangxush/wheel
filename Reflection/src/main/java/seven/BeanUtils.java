package seven;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.apache.commons.lang.StringUtils;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class BeanUtils {
    /**
     * 获取注解方法
     * @param invocation
     * @param clz 注解类型
     * @return 方法，未找到为null
     * @throws Throwable 异常
     */
    public static Method getAnnotationMethod(final MethodInvocation invocation, final Class<? extends Annotation> clz) throws Throwable {
        // 获取接口方法
        final Method interfaceMethod = invocation.getMethod();
        // 获取实现方法
        final Method implementMethod = BeanUtils.getImplMethod(invocation.getThis(), interfaceMethod);
        // 优先获取实现类注解
        if (null != implementMethod && implementMethod.isAnnotationPresent(clz)) {
            return implementMethod;
        }

        if (interfaceMethod.isAnnotationPresent(clz)) {
            return interfaceMethod;
        }
        return null;
    }

    /**
     * 获取实现方法 -- 支持多层代理，支持泛型
     * @param bean 当前对象
     * @param interfaceMethod 接口方法
     * @return 实现方法
     * @throws Throwable 异常
     */
    public static Method getImplMethod(final Object bean, final Method interfaceMethod) throws Throwable {
        final Object orgBean = getTargetBean(bean);
        if (null == orgBean) {
            return null;
        }
        for (final Type type : interfaceMethod.getGenericParameterTypes()) {
            if (type instanceof TypeVariable<?>) {
                for (final Method m : orgBean.getClass().getMethods()) {
                    if (StringUtils.equals(interfaceMethod.getName(), m.getName())
                            && m.getParameterTypes().length == interfaceMethod.getParameterTypes().length
                            && m.getAnnotations().length > 0) {
                        return m;
                    }
                }
            }
        }
        return orgBean.getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
    }

    /**
     * 获取原始对象
     *
     * @param bean 被代理的对象
     * @return 原始对象
     */
    public static Object getTargetBean(final Object bean) {
        Object targetBean = bean;
        try {
            while (targetBean instanceof Advised) {
                final Object target = ((Advised) targetBean).getTargetSource().getTarget();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return targetBean;
    }
}
