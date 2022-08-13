package com.assignment.WebMvc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    public  Optional<T> get(long id);

    public  List<T> getAll(T t);

    public  T save(T t);

    public  T update(T t);

    public  boolean delete(long id);
}
