
export class Hotel {
  id : number;
  tarifaInicial : number;
  cupoMaximo: number;
  comidaMascota : boolean;
  reservas : number;
  constructor
  (
    id : number,
    tarifaInicial : number,
    cupoMaximo : number,
    comidaMascota : boolean,
    reservas : null
    )
    {
    this.id = id;
    this.tarifaInicial = tarifaInicial;
    this.cupoMaximo = cupoMaximo;
    this.comidaMascota = comidaMascota;
  }
}
