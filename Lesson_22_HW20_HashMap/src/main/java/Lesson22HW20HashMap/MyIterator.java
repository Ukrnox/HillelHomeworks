package Lesson22HW20HashMap;

public interface MyIterator<T> {

    boolean hasNext();

    T next();

    void remove();

    T getCurrent();
}
