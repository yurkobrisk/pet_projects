package by.korziuk.tasty_meals_app.service;

import by.korziuk.tasty_meals_app.exception.MealNotFoundException;
import by.korziuk.tasty_meals_app.model.Meal;
import by.korziuk.tasty_meals_app.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    private final MealRepository mealRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Override
    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    @Override
    public Meal findMealById(Long id) {
        return mealRepository
                .findMealById(id)
                .orElseThrow(() -> new MealNotFoundException("Meal by id = " + id + " was not found"));
    }

    @Override
    public void deleteMealById(Long id) {
        mealRepository.deleteMealById(id);
    }
}
