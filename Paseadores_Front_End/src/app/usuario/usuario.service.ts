import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Usuario } from './usuario';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment'
import { Tarjeta } from '../tarjeta/tarjeta';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {


apiUrl = environment.baseUrl + 'usuarios';
constructor(private http: HttpClient) { }

getUsuarios(): Observable<Usuario[]>{
return this.http.get<Usuario[]>(this.apiUrl);
}
createUsuario(newUsuario: Usuario):Observable<Usuario[]>{
  return this.http.post<Usuario[]>(this.apiUrl,newUsuario);

  }

  getUsuarioDetail(usuarioId): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${usuarioId}`);
  }

  getTarjetas(id:number):Observable<Tarjeta[]>{
    return this.http.get<Tarjeta[]>(this.apiUrl+'/'+id+'/tarjetas');


  }

}
