package com.raven.birdmail.Repository;

public interface CrudRepository<T> {
    T byId(Long id);
    void create (T t);
}
