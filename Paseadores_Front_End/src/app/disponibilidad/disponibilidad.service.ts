import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Disponibilidad } from './disponibilidad';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DisponibilidadService {

  createDisponibilidad(newDisponibilidad: Disponibilidad,id:number):Observable<Disponibilidad[]>{
    return this.http.post<Disponibilidad[]>(this.apiUrl+id+'/disponibilidades',newDisponibilidad);
  }
    apiUrl = environment.baseUrl + 'paseadores/';
constructor(private http: HttpClient) { }

getDisponibilidades(id:number): Observable<Disponibilidad[]>{
  return this.http.get<Disponibilidad[]>(this.apiUrl+id+'/disponibilidades');
}
deleteDisponibilidad(idPaseador:number,idDisponibilidad:number):Observable<{}>{
   return this.http.delete(this.apiUrl+idPaseador+'/disponibilidades/'+idDisponibilidad);
}
}
