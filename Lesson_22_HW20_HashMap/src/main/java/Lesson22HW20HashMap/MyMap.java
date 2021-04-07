package Lesson22HW20HashMap;

public interface MyMap<K, V> {
    V put(K k, V v);

    boolean isEmpty();

    boolean containsKey(K k);

    V get(K k);

    V remove(K k);
}
