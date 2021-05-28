import { Haversine } from 'src/app/haversine/haversine';
import { Hotel } from 'src/app/hotel/hotel';
import { Paseo } from '../paseo/paseo';
import { Mascota } from '../mascota/mascota';
import { Factura } from '../factura/factura';

export class Reserva{
  id: number;
  fechaHora: string;
  estadoPago: boolean;
  tipo: string;
  observaciones: string;
  diasEstadia: number;
  factura: Factura;
  hotel: Hotel;
  haversine: Haversine;
  paseo: Paseo;
  mascota: Mascota;
  constructor(
    id: number,
    fechaHora: string,
    estadoPago: boolean,
    tipo: string,
    observaciones: string,
    diasEstadia: number,
    factura: Factura,
    mascota: Mascota,
    hotel: Hotel,
    haversine: Haversine,
    paseo: Paseo){
    this.id = id;
    this.fechaHora = fechaHora;
    this.estadoPago = estadoPago;
    this.tipo = tipo;
    this.observaciones = observaciones;
    this.diasEstadia = diasEstadia;
    this.factura = factura;
    this.hotel = hotel;
    this.haversine = haversine;
    this.paseo = paseo;
    this.mascota = mascota;
  }
}
