package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {


    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        userMealWithExceeds.forEach(System.out::println);// для проверки добавил .stream().forEach(System.out::println);

        System.out.println("----------------------");

        List<UserMealWithExceed> userMealWithExceedsByCycle = getFilteredWithExceededByCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        userMealWithExceedsByCycle.forEach(System.out::println);// для проверки добавил .stream().forEach(System.out::println);


//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        // создаем мап чтобы потом сравнить каллории в заданный день (дата, сумма каллории)
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(ml->ml.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories))

                );

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),
                        caloriesSumByDate.get(meal.getDateTime().toLocalDate())>caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){
        Map<LocalDate,Integer> caloriesSumByDate = new HashMap<>();
        for(UserMeal meal : mealList){
            LocalDate date = meal.getDateTime().toLocalDate();
            caloriesSumByDate.put(date, caloriesSumByDate.getOrDefault(date,0)+meal.getCalories());
        }
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for(UserMeal meal : mealList){
            LocalDateTime mealDate = meal.getDateTime();
            if(TimeUtil.isBetween(mealDate.toLocalTime(),startTime,endTime)){
                userMealWithExceeds.add(new UserMealWithExceed(mealDate,meal.getDescription(),meal.getCalories(), caloriesSumByDate.get(meal.getDateTime().toLocalDate())>caloriesPerDay));
            }
        }
        return userMealWithExceeds;

    }


}
