import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Tarjeta } from './tarjeta';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment'


@Injectable({
  providedIn: 'root'
})
export class TarjetaService {


apiUrl = environment.baseUrl + 'usuarios/';

constructor(private http: HttpClient) {

}


getTarjetas(): Observable<Tarjeta[]>{
 return this.http.get<Tarjeta[]>( this.apiUrl );
}

createTarjeta(newTarjeta: Tarjeta, id:number):Observable<Tarjeta[]>{
  return this.http.post<Tarjeta[]>(this.apiUrl+ id+'/tarjetas',newTarjeta);

  }

}
