package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealDao {

    private Map<Integer, Meal> map = new ConcurrentHashMap<>();
    private AtomicInteger mealIdSequence = new AtomicInteger(1000);

    public MealDaoInMemory() {
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void delete(int mealId) {
        map.remove(mealId);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) meal.setId(getNextId());
        return map.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Meal getById(int mealId) {
        return map.get(mealId);
    }

    private int getNextId() {
        return mealIdSequence.incrementAndGet();
    }
}
