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
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    //{
    //    MealsUtil.MEALS.forEach(this::save);
    // }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if (repository.get(meal.getId()).getUserId() != userId) {
            log.info("meal {} not for user id {}", meal, userId);
            return null;
        } else {
            meal.setUserId(userId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for userId {}", id, userId);
        Meal meal = repository.get(id);
        if (meal == null) {
            return false;
        } else if (meal.getUserId() != userId) {
            log.info("meal with id {} not for user id {}", id, userId);
            return false;
        } else {
            return repository.remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for userId {}", id, userId);
        Meal meal = repository.get(id);
        if (meal == null) {
            log.info("meal with id {} not found", id);
            return null;
        } else if (meal.getUserId() != userId) {
            log.info("meal with id {} not for user id {}", id, userId);
            return null;
        } else {
            return meal;
        }
    }

    @Override
    public List<MealTo> getList(int userId, int caloriesPerDay, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("get all for userId {}", userId);
        LocalDate sd = startDate == null ? LocalDate.MIN : startDate;
        LocalDate ed = endDate == null ? LocalDate.MAX : endDate;
        LocalTime st = startTime == null ? LocalTime.MIN : startTime;
        LocalTime et = endTime == null ? LocalTime.MAX : endTime;
        Map<LocalDate, Integer> caloriesSumByDate = repository.values().stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        return repository.values()
                .stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetween(m.getDate(), sd, ed) &&
                        DateTimeUtil.isBetween(m.getTime(), st, et))
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .map(m -> MealsUtil.createTo(m, caloriesSumByDate.get(m.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}

