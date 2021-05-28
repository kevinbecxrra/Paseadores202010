import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Hotel } from './hotel';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class HotelService {

apiUrl = environment.baseUrl + 'hotels';

constructor(private http: HttpClient) { }

getHotels(): Observable<Hotel[]> {
  return this.http.get<Hotel[]>(this.apiUrl);
}

createHotel (newHotel: Hotel): Observable<Hotel[]>{
  return this.http.post<Hotel[]>(this.apiUrl,newHotel);
}

getHotelDetail(hotelId): Observable<Hotel> {
  return this.http.get<Hotel>(`${this.apiUrl}/${hotelId}`);
}

}
