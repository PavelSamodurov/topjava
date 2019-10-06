package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealDao implements Dao {
    private static volatile MemoryMealDao instance;

    private Map<Integer, Meal> meals;

    private MemoryMealDao() {
        meals = new HashMap<>();
        meals.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        meals.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        meals.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static MemoryMealDao getInstance() {
        if (instance == null) {
            synchronized (MemoryMealDao.class) {
                if (instance == null) {
                    instance = new MemoryMealDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Meal get(int id) {
        return meals.get(id);
    }

    @Override
    public Map getAll() {
        return meals;
    }

    @Override
    public void save(Object o) {
        Meal newMeal = (Meal) o;
        meals.put(newMeal.getId(), newMeal);
    }

    @Override
    public void update(Object o) {
        Meal newMeal = (Meal) o;
        meals.put(newMeal.getId(), newMeal);
    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}
