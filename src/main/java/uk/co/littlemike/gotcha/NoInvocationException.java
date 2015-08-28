package uk.co.littlemike.gotcha;

public class NoInvocationException extends RuntimeException {

    public NoInvocationException(Class<?> proxyClass) {
        super("Attempted to capture invocation from Lambda on " + proxyClass + ", but no method was invoked.");
    }
}
