package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao {
    void delete(int mealId);

    Meal save(Meal meal);

    List<Meal> getAll();

    Meal getById(int mealId);
}
