package by.korziuk.tasty_meals_app.service;

import by.korziuk.tasty_meals_app.model.Meal;

import java.util.List;

public interface MealService {

    Meal addMeal(Meal meal);
    List<Meal> findAllMeals();
    Meal updateMeal(Meal meal);
    Meal findMealById(Long id);
    void deleteMealById(Long id);
}
