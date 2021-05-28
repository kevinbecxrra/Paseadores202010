
export class Haversine {
  id : number;
  horaDePaso : any;
  longitud: any;
  latitud : any;
  paseo : any;
  constructor
  (
    id : number,
    horaDePaso : any,
    longitud : number,
    latitud : number,
    paseo : null
    )
    {
    this.id = id;
    this.horaDePaso = horaDePaso;
    this.longitud = longitud;
    this.latitud = latitud;
  }
}
