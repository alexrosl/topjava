package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleMealDaoImpl implements MealDao {

    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private static MealDao mealDao = new SimpleMealDaoImpl();
    private AtomicInteger mealIdSequence = new AtomicInteger(1000);

    private SimpleMealDaoImpl() {
        Meal meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        map.put(meal.getId(), meal);
        meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        map.put(meal.getId(), meal);
        meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        map.put(meal.getId(), meal);
        meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        map.put(meal.getId(), meal);
        meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        map.put(meal.getId(), meal);
        meal = new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        map.put(meal.getId(), meal);
    }

    public static MealDao getInstance() {
        return mealDao;
    }

    @Override
    public void add(Meal meal) {
        Meal addMeal = new Meal(getNextId(),
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories());
        map.put(addMeal.getId(), addMeal);
    }

    @Override
    public void delete(int mealId) {
        map.remove(mealId);
    }

    @Override
    public void update(Meal meal) {
        map.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> meals = new ArrayList<>();
        for (Map.Entry<Integer, Meal> entry : map.entrySet()) {
            meals.add(entry.getValue());
        }
        return meals;
    }

    @Override
    public Meal getById(int mealId) {
        return map.get(mealId);
    }

    private int getNextId() {
        return mealIdSequence.incrementAndGet();
    }
}
