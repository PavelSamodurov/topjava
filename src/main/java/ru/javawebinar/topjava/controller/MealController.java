package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface MealController {
    Meal get(int id);

    List getAll();

    void save(LocalDateTime dateTime, String description, int calories);

    void update(Meal meal);

    void delete(int id);
}
