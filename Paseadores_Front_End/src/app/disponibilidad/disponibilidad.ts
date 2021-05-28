
import {Paseador} from "../paseador/paseador";

export class Disponibilidad {
 id: number;
 fechaInicio: any;
 duracion: number;
 paseador: Paseador;



 constructor(
  id: number,
  fechaInicio: any,
  duracion: number,
  paseador: Paseador,
 ) {
  this.id= id;
this.fechaInicio=fechaInicio;
this.duracion = duracion;
this.paseador = paseador;
 }
}
