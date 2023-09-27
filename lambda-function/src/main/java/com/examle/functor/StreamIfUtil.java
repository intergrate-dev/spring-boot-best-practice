package com.example.functor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * StreamIfUtil
 *
 * @author xxx
 * @since 2023/9/20 14:19
 */
public class StreamIfUtil<T> {
    private Stream<T> stream;

    private List<Predicate> predicates = new ArrayList<>();

    private int count = 0;

    public StreamIfUtil(Stream<T> stream) {
        this.stream = stream;
    }

    public StreamIfUtil(Stream<T> stream, List<Predicate> predicates, int count) {
        this.stream = stream;
        this.predicates = predicates;
        this.count = count;
    }

    public StreamIfUtil<T> select(Predicate<T> predicate) {
        this.predicates.add(predicate);
        return this;
    }

    //   实现了functor模式，functor模式的方法，是参入函数，然后解包，对包里的元素进行传入参数运算，最后，将结果包装返回。
    public StreamIfUtil<T> with(Consumer<T> consumer) throws Exception {
        this.count++;
        final int port = this.count;
        if (this.predicates.isEmpty() || this.predicates.size() > this.count) throw new Exception("必须先调用select方法!");
        Stream<T> stream =
                this.stream.peek(
                        t -> {
                            if (this.predicates.get(port - 1).test(t)) {
                                consumer.accept(t);
                            }
                        });
        return new StreamIfUtil<>(stream, this.predicates, this.count);
    }

    public StreamIfUtil<T> elseWith(Consumer<T> consumer) throws Exception {
        if (this.predicates.isEmpty()) throw new Exception("必须先调用select方法!");
        Stream<T> stream =
                this.stream.peek(
                        t -> {
                            if (elseTest(t)) {
                                consumer.accept(t);
                            }
                        });
        return new StreamIfUtil<>(stream);
    }

    private boolean elseTest(T t) {
        return this.predicates.stream().allMatch(predicate -> !predicate.test(t));
    }

    public Stream<T> getStream() {
        return stream;
    }
}
