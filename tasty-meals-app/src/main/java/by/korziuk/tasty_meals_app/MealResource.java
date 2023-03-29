package by.korziuk.tasty_meals_app;

import by.korziuk.tasty_meals_app.model.Meal;
import by.korziuk.tasty_meals_app.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealResource {
    private final MealService mealService;

    public MealResource(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Meal>> getAllMeals() {
        List<Meal> meals = mealService.findAllMeals();
        return new ResponseEntity<>(meals, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Meal> getMealById(@PathVariable("id") Long id) {
        Meal meal = mealService.findMealById(id);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) {
        Meal newMeal = mealService.addMeal(meal);
        return new ResponseEntity<>(newMeal, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Meal> updateMeal(@RequestBody Meal meal) {
        Meal updateMeal = mealService.updateMeal(meal);
        return new ResponseEntity<>(updateMeal, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
