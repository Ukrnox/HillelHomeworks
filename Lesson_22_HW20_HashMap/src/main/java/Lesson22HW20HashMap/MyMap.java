package Lesson22HW20HashMap;

public interface MyMap<K, V> {
    void put(K k, V v);

    boolean isEmpty();

    boolean containsKey(K k);

    V get(K k);

    boolean remove(K k);
}
