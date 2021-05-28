import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Paseador } from './paseador';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class PaseadorService {
apiUrl = environment.baseUrl +'paseadores';
constructor(private http: HttpClient) { }

 getPaseadores():Observable<Paseador[]>{
   return this.http.get<Paseador[]>(this.apiUrl);
 }
 createPaseador(newPaseador: Paseador) {
  return this.http.post<Paseador>(this.apiUrl,newPaseador);
  }

  getPaseadorDetail(paseadorId): Observable<Paseador> {
    return this.http.get<Paseador>(`${this.apiUrl}/${paseadorId}`);
  }
}
