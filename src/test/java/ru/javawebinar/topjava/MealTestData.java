package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static Meal MEAL_USER_100002 = new Meal(START_SEQ + 2, LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 0), "Завтрак", 500);
    public static Meal MEAL_USER_100003 = new Meal(START_SEQ + 3, LocalDateTime.of(2019, Month.OCTOBER, 21, 13, 0), "Обед", 1000);
    public static Meal MEAL_USER_100004 = new Meal(START_SEQ + 4, LocalDateTime.of(2019, Month.OCTOBER, 21, 18, 0), "Ужин", 500);
    public static Meal MEAL_USER_100005 = new Meal(START_SEQ + 5, LocalDateTime.of(2019, Month.OCTOBER, 22, 10, 0), "Завтрак", 500);
    public static Meal MEAL_USER_100006 = new Meal(START_SEQ + 6, LocalDateTime.of(2019, Month.OCTOBER, 22, 13, 0), "Обед", 1000);
    public static Meal MEAL_USER_100007 = new Meal(START_SEQ + 7, LocalDateTime.of(2019, Month.OCTOBER, 22, 18, 0), "Ужин", 510);

    public static final List<Meal> USERS_MEALS = Arrays.asList(
            MEAL_USER_100007,
            MEAL_USER_100006,
            MEAL_USER_100005,
            MEAL_USER_100004,
            MEAL_USER_100003,
            MEAL_USER_100002);

    public static Meal MEAL_ADMIN_100008 = new Meal(START_SEQ + 8, LocalDateTime.of(2019, Month.OCTOBER, 21, 10, 0), "Завтрак", 500);
    public static Meal MEAL_ADMIN_100009 = new Meal(START_SEQ + 9, LocalDateTime.of(2019, Month.OCTOBER, 21, 13, 0), "Обед", 1000);
    public static Meal MEAL_ADMIN_1000010 = new Meal(START_SEQ + 10, LocalDateTime.of(2019, Month.OCTOBER, 21, 18, 0), "Ужин", 500);
    public static Meal MEAL_ADMIN_1000011 = new Meal(START_SEQ + 11, LocalDateTime.of(2019, Month.OCTOBER, 22, 10, 0), "Завтрак", 500);
    public static Meal MEAL_ADMIN_1000012 = new Meal(START_SEQ + 12, LocalDateTime.of(2019, Month.OCTOBER, 22, 13, 0), "Обед", 1000);
    public static Meal MEAL_ADMIN_1000013 = new Meal(START_SEQ + 13, LocalDateTime.of(2019, Month.OCTOBER, 22, 18, 0), "Ужин", 510);

    public static final List<Meal> ADMINS_MEALS = Arrays.asList(
            MEAL_ADMIN_1000013,
            MEAL_ADMIN_1000012,
            MEAL_ADMIN_1000011,
            MEAL_ADMIN_1000010,
            MEAL_ADMIN_100009,
            MEAL_ADMIN_100008);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.hashCode()).isEqualTo(expected.hashCode());
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual.hashCode()).isEqualTo(expected.hashCode());
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
}
