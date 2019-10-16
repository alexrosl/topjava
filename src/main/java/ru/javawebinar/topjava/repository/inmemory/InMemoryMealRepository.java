package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if (!isPresent(meal.getId(), userId)) {
            return null;
        } else {
            return repository.get(userId).put(meal.getId(), meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for userId {}", id, userId);
        if (!isPresent(id, userId)) {
            return false;
        } else {
            return repository.get(userId).remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for userId {}", id, userId);
        if (!isPresent(id, userId)) {
            return null;
        } else {
            return repository.get(userId).get(id);
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(meal -> true, userId);
    }

    @Override
    public List<Meal> getFiltered(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("get all for userId {}", userId);
        return getFiltered(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate), userId);
    }

    private List<Meal> getFiltered(Predicate<Meal> predicate, int userId) {
        return repository.get(userId)
                .values()
                .stream()
                .filter(predicate)
                .sorted((m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime()))
                .collect(Collectors.toList());
    }

    private boolean isPresent(int id, int userId) {
        Map<Integer, Meal> map = repository.get(userId);
        boolean result = map != null && map.get(id) != null;
        if (!result) {
            log.info("meal with id={} not found for userId={}", id, userId);
        }
        return result;
    }
}

