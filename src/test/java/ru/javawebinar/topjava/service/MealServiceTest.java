package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_USER_100002.getId(), USER.getId());
        assertMatch(meal, MEAL_USER_100002);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_USER_100002.getId(), ADMIN.getId());
    }

    @Test
    public void delete() {
        service.delete(MEAL_USER_100002.getId(), USER.getId());
        assertMatch(service.getAll(USER.getId()),
                MEAL_USER_100007,
                MEAL_USER_100006,
                MEAL_USER_100005,
                MEAL_USER_100004,
                MEAL_USER_100003);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(MEAL_USER_100002.getId(), ADMIN.getId());
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(LocalDate.of(2019, Month.OCTOBER, 21),
                LocalDate.of(2019, Month.OCTOBER, 21), USER.getId()),
                MEAL_USER_100004,
                MEAL_USER_100003,
                MEAL_USER_100002);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER.getId()), USERS_MEALS);
        assertMatch(service.getAll(ADMIN.getId()), ADMINS_MEALS);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_USER_100002);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(updated.getCalories() + 50);
        service.update(updated, USER.getId());
        assertMatch(service.get(updated.getId(), USER.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        Meal updated = new Meal(MEAL_USER_100002);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(updated.getCalories() + 50);
        service.update(updated, ADMIN.getId());
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 0), "Завтрак", 500);
        Meal created = service.create(newMeal, USER.getId());
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
    }
}