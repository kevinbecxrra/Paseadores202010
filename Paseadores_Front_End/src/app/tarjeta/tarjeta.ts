import { Usuario } from '../usuario/usuario';

export class Tarjeta {
  id: number;
  tipo: string;
  numeroTarjeta: number;
  codigoSeguridad: number;
  fechaVencimiento: string;
  usuario: Usuario;
  constructor(
    id: number,
    tipo: string,
    numeroTarjeta: number,
    codioSeguridad: number,
    fechaVencimiento: string,
    usuario: Usuario
  ){
    this.id = id;
    this.tipo = tipo;
    this.numeroTarjeta = numeroTarjeta;
    this.codigoSeguridad = codioSeguridad;
    this.fechaVencimiento = fechaVencimiento;
    this.usuario = usuario;

  }
}
