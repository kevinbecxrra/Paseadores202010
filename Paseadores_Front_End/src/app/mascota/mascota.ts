import { Reserva } from '../reserva/reserva';
import { Usuario } from '../usuario/usuario';

export class Mascota {

  id: number;
  nombre: string;
  foto: string;
  raza: string;
  color: string;
  seniasParticulares: string;
  historiaClinica: string;
  edad: number;
  sexo: boolean;
  usuario: Usuario;
  reservas: Reserva;

  constructor(
    id: number,
    nombre: string,
    raza: string,
    color: string,
    seniasParticulares: string,
    historiaClinica: string,
    edad: number,
    sexo: boolean,
    foto: string,
    usuario: Usuario,
    reservas: any) {
    this.id = id;
    this.nombre = nombre;
    this.raza = raza;
    this.color = color;
    this.seniasParticulares = seniasParticulares;
    this.historiaClinica = historiaClinica;
    this.edad = edad;
    this.sexo = sexo;
    this.foto = foto;
    this.usuario = usuario;
    this.reservas = reservas;
  }

  setUsuario(user: Usuario):void {
    this.usuario=user;
  }
}
