import { Paseo } from '../paseo/paseo';
import { Paseador } from '../paseador/paseador';

export class Rating {
  id: number;
  estrellas:number;
  comentario: string;
  paseo: Paseo;
  paseador:Paseador;

  constructor(
    id: number,
    estrellas:number,
    comentario: string,
    paseo: Paseo,
    paseador:Paseador
  ){
    this.id = id;
    this.estrellas=estrellas;
    this.comentario=comentario;
    this.paseo=paseo;
    this.paseador=null;
  }
}
