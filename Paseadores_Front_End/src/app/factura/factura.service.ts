import { Factura } from './factura';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FacturaService {
    private apiUrl = environment.baseUrl + 'facturas';
    constructor(private http: HttpClient) { }
    getFacturas(): Observable<Factura[]>{
      return this.http.get<Factura[]>(this.apiUrl);
    }
    getFacturaDetail(facturaId:number): Observable<Factura>{
      return this.http.get<Factura>(`${this.apiUrl}/${facturaId}`);
    }
    createFactura(factura:Factura):Observable<Factura[]>{
      return this.http.post<Factura[]>(this.apiUrl,factura);
    }
}
