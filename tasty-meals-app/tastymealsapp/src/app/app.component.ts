import { Component, OnInit } from '@angular/core';
import { Meal } from './meal';
import { MealService } from './meal.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public meals: Meal[] | undefined;
  public editMeal: Meal | undefined;

  constructor(private mealService: MealService){}

  ngOnInit() {
      this.getMeals();
  }

  public getMeals(): void {
    this.mealService.getMeals().subscribe(
      (response: Meal[]) => {
        this.meals = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddMeal(addForm: NgForm): void {
    document.getElementById('add-meal-form')?.click;
    this.mealService.addMeal(addForm.value).subscribe({
      next: (response: Meal) => {
        console.log(response);
        this.getMeals();
        addForm.reset();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    });
  }

  public onUpdateMeal(meal: Meal): void {    
    this.mealService.updateMeal(meal).subscribe({
      next: (response: Meal) => {
        console.log(response);
        this.getMeals();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      }
    });
  }

  public onDeleteMeal(mealId: number): void {    
    this.mealService.deleteMeal(mealId).subscribe({
      next: (response: void) => {
        console.log(response);
        this.getMeals();
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message)
      }
    });
  }

  public onOpenModal(meal: Meal, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addMealModal');
    }
    if (mode === 'edit') {
      this.editMeal = meal;
      button.setAttribute('data-target', '#updateMealModal');
    }
    if (mode === 'delete') {
      button.setAttribute('data-target', '#deleteMealModal');
    }
    container?.appendChild(button);
    button.click();
  }

}
