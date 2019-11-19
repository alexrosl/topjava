package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractUserServiceJpaUtilRequiredTest extends AbstractUserServiceTest {
    @Autowired
    protected JpaUtil jpaUtil;

}
