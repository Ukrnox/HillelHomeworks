package Lesson22HW20HashMap;

@FunctionalInterface
public interface MyBiFunction <T, U, R>{
    R apply(T t, U u);
}
