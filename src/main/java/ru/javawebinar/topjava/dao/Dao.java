package ru.javawebinar.topjava.dao;

import java.util.Map;

public interface Dao<K, T> {

    T get(int id);

    Map<K, T> getAll();

    void save(T t);

    void update(T t);

    void delete(int id);
}
