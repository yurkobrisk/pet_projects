package by.korziuk.tasty_meals_app.repository;

import by.korziuk.tasty_meals_app.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    void deleteMealById(Long id);

    Optional<Meal> findMealById(Long id);
}
