package uk.co.littlemike.gotcha;

import java.lang.reflect.Method;

public class Invocation {
    private final Method method;
    private final Object[] arguments;

    public Invocation(Method method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
