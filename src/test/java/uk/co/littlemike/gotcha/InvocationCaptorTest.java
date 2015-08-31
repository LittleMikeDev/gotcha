package uk.co.littlemike.gotcha;

import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class InvocationCaptorTest {
    public static class ProxiedClass {
        public void noArgs() {
            throw new IllegalStateException("Methods should never actually be invoked!");
        }

        public void method(String argument1, int argument2) {
            throw new IllegalStateException(String.format(
                    "Methods should never actually be invoked! Invoked with arguments %s, %s", argument1, argument2));
        }
    }

    private static Method METHOD;
    private static Method NOARGS;

    @BeforeClass
    public static void setConstants() throws NoSuchMethodException {
        METHOD = ProxiedClass.class.getMethod("method", String.class, int.class);
        NOARGS = ProxiedClass.class.getMethod("noArgs");
    }

    @Test
    public void doesNotReturnAnInvocationIfNoMethodInvoked() {
        // Given
        InvocationCaptor<ProxiedClass> captor = InvocationCaptor.forClass(ProxiedClass.class);

        // Then
        assertThat(captor.getLastInvocation()).isEmpty();
    }

    @Test
    public void capturesNoArgsMethodInvocation() throws NoSuchMethodException {
        // Given
        InvocationCaptor<ProxiedClass> captor = InvocationCaptor.forClass(ProxiedClass.class);
        ProxiedClass proxy = captor.getProxy();

        // When
        proxy.noArgs();

        // Then
        Invocation invocation = captor.getLastInvocation().get();
        assertThat(invocation.getMethod()).isEqualTo(NOARGS);
        assertThat(invocation.getArguments()).isEmpty();
    }

    @Test
    public void capturesArguments() throws NoSuchMethodException {
        // Given
        InvocationCaptor<ProxiedClass> captor = InvocationCaptor.forClass(ProxiedClass.class);
        ProxiedClass proxy = captor.getProxy();
        String argument1 = "Argument 1";
        int argument2 = 4;

        // When
        proxy.method(argument1, argument2);

        // Then
        Invocation invocation = captor.getLastInvocation().get();
        assertThat(invocation.getMethod()).isEqualTo(METHOD);
        assertThat(invocation.getArguments()).containsExactly(argument1, argument2);
    }

    @Test
    public void capturesArgumentsFromGivenLambda() throws NoSuchMethodException {
        // When
        String argument1 = "Arg1";
        int argument2 = 2;
        Invocation invocation = InvocationCaptor.capture(ProxiedClass.class, p -> p.method(argument1, argument2));

        // Then
        assertThat(invocation.getMethod()).isEqualTo(METHOD);
        assertThat(invocation.getArguments()).containsExactly(argument1, argument2);
    }

    @Test(expected = NoInvocationException.class)
    public void throwsExceptionIfAttemptingToCaptureInvocationThatNeverHappened() {
        InvocationCaptor.capture(ProxiedClass.class, p -> {
        });
    }

    @Test
    public void canGetMethods() {
        assertThat(Methods.get(ProxiedClass.class, ProxiedClass::noArgs)).isEqualTo(NOARGS);
        assertThat(Methods.get(ProxiedClass.class, c -> c.method("", 0))).isEqualTo(METHOD);
    }
}
