package com.example.functor;

import java.util.function.Function;

/**
 * Functor
 *
 * @author xxx
 * @since 2023/9/20 16:56
 */
public interface Functor<T, F extends Functor<?, ?>> {
 <R> F map(Function<T, R> f);
}
