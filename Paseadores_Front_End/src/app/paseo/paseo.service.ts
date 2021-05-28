import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Paseo } from './paseo';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class PaseoService {

  apiUrl = environment.baseUrl + 'paseos';
constructor(private http: HttpClient) { }

getPaseos(): Observable<Paseo[]>
{
  return this.http.get<Paseo[]>(this.apiUrl);
}

createPaseo(newPaseo: Paseo) {
  return this.http.post<Paseo>(this.apiUrl,newPaseo);
  }

  getPaseoDetail(paseoId): Observable<Paseo> {
    return this.http.get<Paseo>(`${this.apiUrl}/${paseoId}`);
  }

}
