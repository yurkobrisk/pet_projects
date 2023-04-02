import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meal } from './meal';
import { environment } from 'src/environments/environments';


@Injectable({
    providedIn: 'root'
})
export class MealService {
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getMeals(): Observable<Meal[]> {
        return this.http.get<Meal[]>(`${this.apiServerUrl}/meal/all`);
    }

    public addMeal(meal: Meal): Observable<Meal> {
        return this.http.post<Meal>(`${this.apiServerUrl}/meal/add`, meal);
    }

    public updateMeal(meal: Meal): Observable<Meal> {
        return this.http.put<Meal>(`${this.apiServerUrl}/meal/update`, meal);
    }

    public deleteMeal(mealId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/meal/delete/${mealId}`);
    }
}