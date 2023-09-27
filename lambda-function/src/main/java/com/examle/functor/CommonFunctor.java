package com.example.functor;

import java.util.function.Function;

/**
 * CommonFunctor
 *
 * @author xxx
 * @since 2023/9/20 16:57
 */
public class CommonFunctor<T> implements Functor<T, CommonFunctor<?>> {
    private T value;
    public CommonFunctor(T value) {
        this.value = value;
    }
    @Override
    public <R> CommonFunctor<R> map(Function<T, R> f) {
        final R result = f.apply(value);
        return new CommonFunctor<>(result);
    }
    public T getValue() {
        return value;
    }
}
