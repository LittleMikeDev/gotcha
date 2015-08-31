# Gotcha

[![Build Status](https://travis-ci.org/LittleMikeDev/gotcha.svg)](https://travis-ci.org/LittleMikeDev/gotcha)
[![codecov.io](http://codecov.io/github/LittleMikeDev/gotcha/coverage.svg?branch=master)](http://codecov.io/github/LittleMikeDev/gotcha?branch=master)

Library for capturing method invocations. Can be used with Java 8 lambdas to allow type-safe method references.

Note: this is still a WIP, and therefore the API may change.

## Usage

Capture a method invocation with arguments.

```
Invocation invocation = InvocationCaptor.capture(MyClass.class, c -> c.someMethod(argument1, argument2));
Method method = invocation.getMethod();
Object[] arguments = invocation.getArguments();
```

Capture last invocation on proxy (similar to mocking frameworks)

```
InvocationCaptor captor = InvocationCaptor.forClass(MyClass.class);
MyClass proxy = captor.getProxy();
proxy.doSomething(1, 2);
Optional<Invocation> invocation = captor.getLastInvocation();
```
