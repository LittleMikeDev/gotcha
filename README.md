# Gotcha

[![Build Status](https://travis-ci.org/LittleMikeDev/gotcha.svg)](https://travis-ci.org/LittleMikeDev/gotcha)
[![codecov.io](http://codecov.io/github/LittleMikeDev/gotcha/coverage.svg?branch=master)](http://codecov.io/github/LittleMikeDev/gotcha?branch=master)

Library for capturing method invocations. Can be used with Java 8 lambdas to allow type-safe method references.

Note: this is still a WIP, and therefore the API may change.

## Usage

Get a method

```
Method method1 = Methods.get(MyClass.class, MyClass::noArgsMethod);
Method method2 = Methods.get(MyClass.class, c -> c.methodWithArgs(arg1, arg2));
```

Capture a method invocation with arguments.

```
Invocation invocation = InvocationCaptor.capture(MyClass.class, c -> c.someMethod(arg1, arg2));
Method method = invocation.getMethod();
Object[] arguments = invocation.getArguments();
```

Capture multiple invocations using a single captor

```
InvocationCaptor<MyClass> captor = InvocationCaptor.forClass(MyClass.class);
Invocation invocation1 = captor.capture(MyClass:noArgsMethod);
Invocation invocation2 = captor.capture(c -> c.someMethod(arg1, arg2));
```

Capture last invocation on proxy (similar to mocking frameworks)

```
InvocationCaptor captor = InvocationCaptor.forClass(MyClass.class);
MyClass proxy = captor.getProxy();

// Could be called elsewhere
proxy.doSomething(1, 2);

Optional<Invocation> invocation = captor.getLastInvocation();
```
