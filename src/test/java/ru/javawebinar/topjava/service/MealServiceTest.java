package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

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
        Meal meal = service.get(MEAL2_ID, USER_ID);
        assertMatch(meal, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL2_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL3_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL2, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL3_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL4);
        updated.setDescription("UpdateDescription");
        updated.setCalories(240);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL4_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEAL4);
        updated.setDescription("UpdateDescription");
        updated.setCalories(240);
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "New meal", 5000);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() {
        service.create(new Meal(service.get(MEAL1_ID,USER_ID).getDateTime(), "New meal", 5000),USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> filtered = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.MAX, USER_ID);
        assertMatch(filtered, MEAL6, MEAL5, MEAL4);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, MEAL8, MEAL7);
    }
}