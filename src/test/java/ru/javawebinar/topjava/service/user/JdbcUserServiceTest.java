package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.meal.MealServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends MealServiceTest {
}
