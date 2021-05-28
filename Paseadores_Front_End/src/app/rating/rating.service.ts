import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Rating } from './rating';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
apiUrl = environment.baseUrl + 'ratings';
constructor(private http: HttpClient) { }
getRatings(): Observable<Rating[]> {
  return this.http.get<Rating[]>(this.apiUrl);
}
createRating(newRating:Rating):Observable<Rating> {
  return this.http.post<Rating>(this.apiUrl,newRating);
  }
}
