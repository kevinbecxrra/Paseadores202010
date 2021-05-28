import { Rating } from '../rating/rating';
import { Paseador } from '../paseador/paseador';
import { Haversine } from '../haversine/haversine';
import { Reserva } from '../reserva/reserva';

export class Paseo {
  id: number;
  fechaHora: string;
  duracion: number;
  cupoMax: number;
  ratingProm: number;
  completado: boolean;
  lleno: boolean;
  descuento: number;
  ratings:Rating[];
  paseador:Paseador;
  haversine:Haversine[];
  reserva:Reserva[];

  constructor(
  id: number,
  fechaHora: string,
  duracion: number,
  cupoMax: number,
  ratingProm: number,
  completado: boolean,
  lleno: boolean,
  descuento: number,
  ratings:Rating[],
  paseador:Paseador,
  haversine:Haversine[],
  reserva:Reserva[]
  ){
    this.id = id;
    this.fechaHora=fechaHora;
    this.duracion=duracion;
    this.cupoMax =cupoMax;
    this.ratingProm = ratingProm;
    this.completado=completado;
    this.lleno=lleno;
    this.descuento=descuento;
    this.ratings =ratings;
    this.paseador = paseador;
    this.haversine = haversine;
    this.reserva = reserva;
  }
}
