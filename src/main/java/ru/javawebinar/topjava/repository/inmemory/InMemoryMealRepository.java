package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    //{
    //    MealsUtil.MEALS.forEach(m -> this.save(m, m.getUserId()));
    //}

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            usersMeal(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if (!isPresent(meal.getId(), userId)) {
            return null;
        } else {
            meal.setUserId(userId);
            return usersMeal(userId).put(meal.getId(), meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for userId {}", id, userId);
        if (!isPresent(id, userId)) {
            return false;
        } else {
            return usersMeal(userId).remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for userId {}", id, userId);
        if (!isPresent(id, userId)) {
            return null;
        } else {
            return usersMeal(userId).get(id);
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(LocalDate.MIN, LocalDate.MAX, userId);
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("get all for userId {}", userId);
        LocalDate sd = startDate == null ? LocalDate.MIN : startDate;
        LocalDate ed = endDate == null ? LocalDate.MAX : endDate;
        return usersMeal(userId)
                .values()
                .stream()
                .filter(m -> DateTimeUtil.isBetween(m.getDate(), startDate, endDate))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean isPresent(int id, int userId) {
        boolean result = false;
        try {
            result = usersMeal(userId).get(id) != null;
        } catch (NullPointerException e) {
        }
        if (!result) {
            log.info("meal with id={} not found for userId={}", id, userId);
        }
        return result;
    }

    private Map<Integer, Meal> usersMeal(int userId) {
        Map<Integer, Meal> mapMeal = repository.get(userId);
        if (mapMeal == null) {
            repository.put(userId, new ConcurrentHashMap<>());
        }
        return repository.get(userId);
    }
}

