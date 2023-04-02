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


}
