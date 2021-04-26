package org.hillel.dao;

public interface Dao<T>
{
    void create(T entity);
    T read(long id);
    void update(T entity);
    boolean delete(long id);
}
