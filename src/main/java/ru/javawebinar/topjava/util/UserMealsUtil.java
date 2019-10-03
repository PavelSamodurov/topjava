package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.*;
//import ru.javawebinar.topjava.model.UserMeal;
//import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredMealWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
            filteredMealWithExceeded.forEach(System.out::println);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime))
                .map(x -> new UserMealWithExceed(x.getDateTime(),x.getDescription(),x.getCalories(),checkExceeded(mealList,caloriesPerDay,x.getDateTime().toLocalDate())))
                .collect(Collectors.toList());

    }

    private static boolean checkExceeded(List<UserMeal> mealList, int caloriesPerDay, LocalDate date){
        int sum = mealList.stream()
                .filter((userMeal) -> userMeal.getDateTime().toLocalDate().equals(date))
                .map(UserMeal::getCalories)
                .reduce(0, Integer::sum);
        return sum <= caloriesPerDay;
    }
}
