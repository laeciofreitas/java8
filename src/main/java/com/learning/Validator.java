package com.learning;

@FunctionalInterface
public interface Validator<T> {

    boolean validate(T t);

}
