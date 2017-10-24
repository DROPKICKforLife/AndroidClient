package com.dropkick.soma.somaproject.util;

/**
 * Created by junhoe on 2017. 10. 21..
 */

public class SwapPair<V> {

    private V first;
    private V second;
    private boolean isFirst;
    public SwapPair(V first, V second) {
        this.first = first;
        this.second = second;
        isFirst = true;
    }

    public V swapAndGet() {
        isFirst = !isFirst;
        return get();
    }

    public V get() {
        return isFirst ? first : second;
    }

    public void swap() {
        isFirst = !isFirst;
    }

    public static <V> SwapPair<V> of(V first, V second) {
        return new SwapPair<>(first, second);
    }

}
