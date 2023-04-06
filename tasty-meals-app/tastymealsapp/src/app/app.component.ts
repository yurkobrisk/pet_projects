import { Component, OnInit } from '@angular/core';
import { Meal } from './meal';
import { MealService } from './meal.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public meals: Meal[] = [];

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
      button.setAttribute('data-target', '#updateMealModal');
    }
    if (mode === 'delete') {
      button.setAttribute('data-target', '#deleteMealModal');
    }
    container?.appendChild(button);
    button.click();
  }

}
