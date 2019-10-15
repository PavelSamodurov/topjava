package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;
    @Autowired
    private SecurityUtil securityUtil;

    public List<MealTo> getAll() {
        log.info("getAll");
        return MealsUtil.getTos(service.getAll(securityUtil.authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getAll(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getAll");
        List<MealTo> listMealTo = this.getAll();
        return listMealTo.stream()
                .filter(mealTo -> DateTimeUtil.isBetween(mealTo.getDateTime().toLocalDate(), startDate, endDate))
                .filter(mealTo -> DateTimeUtil.isBetween(mealTo.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, securityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        return service.create(meal);
    }

    public Meal update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        checkNotFound(service.get(id, securityUtil.authUserId()), String.format("id = %d for userId = %d", id, securityUtil.authUserId()));
        return service.update(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, securityUtil.authUserId());
    }

}