package uk.co.littlemike.gotcha;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class Methods {
    public static <T> Method get(Class<T> clazz, Consumer<T> method) {
        return InvocationCaptor.capture(clazz, method).getMethod();
    }
}
