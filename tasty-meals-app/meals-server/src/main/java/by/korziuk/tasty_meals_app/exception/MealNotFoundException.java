package by.korziuk.tasty_meals_app.exception;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(String message) {
        super(message);
    }
}
