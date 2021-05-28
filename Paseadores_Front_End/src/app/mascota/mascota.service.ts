import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Mascota } from './mascota';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment'


@Injectable({
  providedIn: 'root'
})
export class MascotaService {

  createMascota(newMascota: Mascota):Observable<Mascota[]>{
    return this.http.post<Mascota[]>(this.apiUrl,newMascota);

    }

apiUrl = environment.baseUrl + 'mascotas';
constructor(private http: HttpClient) { }

getMascotas(): Observable<Mascota[]>{
return this.http.get<Mascota[]>(this.apiUrl);
}


getMascotaDetail(usuarioId): Observable<Mascota> {
  return this.http.get<Mascota>(`${this.apiUrl}/${usuarioId}`);
}

}
