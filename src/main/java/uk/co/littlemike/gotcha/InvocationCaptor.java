package uk.co.littlemike.gotcha;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;

public class InvocationCaptor<T> {
    private class InvocationHandler implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
            return lastInvocation = Optional.of(new Invocation(method, arguments));
        }

    }
    private final T proxy;
    private Optional<Invocation> lastInvocation = Optional.empty();

    @SuppressWarnings("unchecked")
    public InvocationCaptor(Class<T> proxiedClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxiedClass);
        enhancer.setCallback(new InvocationHandler());
        proxy = (T) enhancer.create();
    }

    public static <T> InvocationCaptor<T> forClass(Class<T> proxiedClass) {
        return new InvocationCaptor<>(proxiedClass);
    }

    public T getProxy() {
        return proxy;
    }

    public Optional<Invocation> getLastInvocation() {
        return lastInvocation;
    }

    public static <T> Invocation capture(Class<T> proxiedClass, Consumer<T> consumer) {
        InvocationCaptor<T> captor = InvocationCaptor.forClass(proxiedClass);
        consumer.accept(captor.getProxy());
        return captor.getLastInvocation()
                .orElseThrow(() -> new NoInvocationException(proxiedClass));
    }
}
