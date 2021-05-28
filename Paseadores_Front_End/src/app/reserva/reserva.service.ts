import { environment } from 'src/environments/environment';
import { Reserva } from './reserva';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {
    private apiUrl = environment.baseUrl + 'mascotas/101/reservas';
    constructor(private http: HttpClient) { }
    getReservas(): Observable<Reserva[]>{
      return this.http.get<Reserva[]>(this.apiUrl);
    }
    getReservaDetail(reservaId:number): Observable<Reserva>{
      return this.http.get<Reserva>(`${this.apiUrl}/${reservaId}`);
    }
    createReserva(reserva:Reserva): Observable<Reserva[]>{
      return this.http.post<Reserva[]>(this.apiUrl,reserva);
    }
}
