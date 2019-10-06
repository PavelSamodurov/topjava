package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealController implements MealController {
    private static AtomicInteger nextId = new AtomicInteger(7);

    Dao<Integer, Meal> mealDao = MemoryMealDao.getInstance();

    @Override
    public Meal get(int id) {
        return mealDao.get(id);
    }

    @Override
    public List getAll() {
        Map<Integer, Meal> meals = mealDao.getAll();
        return new ArrayList(Arrays.asList(meals.values().toArray()));
    }

    @Override
    public void save(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(nextId.getAndIncrement(), dateTime, description, calories);
        mealDao.save(meal);
    }

    @Override
    public void update(Meal meal) {
        mealDao.update(meal);
    }

    @Override
    public void delete(int id) {
        mealDao.delete(id);
    }
}
