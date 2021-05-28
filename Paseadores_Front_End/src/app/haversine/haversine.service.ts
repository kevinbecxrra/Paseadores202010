import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Haversine } from './haversine';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HaversineService {
apiUrl = environment.baseUrl + 'haversines';

constructor(private http: HttpClient) { }

getHaversines(): Observable<Haversine[]> {
  return this.http.get<Haversine[]>(this.apiUrl);
}

createHaversine(newHaversine: Haversine):Observable<Haversine[]>{
  return this.http.post<Haversine[]>(this.apiUrl,newHaversine);
  }

  getHaversineDetail(haversineId): Observable<Haversine> {
    return this.http.get<Haversine>(`${this.apiUrl}/${haversineId}`);
  }

}
