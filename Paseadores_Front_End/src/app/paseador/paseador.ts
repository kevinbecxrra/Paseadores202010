import { Disponibilidad } from "../disponibilidad/disponibilidad";
import {Paseo} from "../paseo/paseo";
import {Rating} from "../rating/rating";

export class Paseador {
 id: number;
 identificacion: number;
 nombre: string;
 infoEPS: string;
 ARL: string;
 telefono: number;
 correo: string;
 costoPaseo: any;
 disponibilidad: Disponibilidad[];
 paseo: Paseo;
 rating: Rating[];



 constructor(
  id: number,
 identificacion: number,
 nombre: string,
 infoEPS: string,
 ARL: string,
 telefono: number,
 correo: string,
 costoPaseo: any,
 disponibilidad: Disponibilidad[],
 paseo: Paseo,
 rating: Rating[],
 ) {
  this.id= id;
  this.identificacion= identificacion;
  this.nombre = nombre;
  this.infoEPS= infoEPS;
  this.ARL = ARL;
  this.telefono = telefono;
  this.correo = correo;
  this.costoPaseo= costoPaseo;
  this.disponibilidad=disponibilidad;
  this.paseo= paseo;
  this.rating = rating;
 }
}
