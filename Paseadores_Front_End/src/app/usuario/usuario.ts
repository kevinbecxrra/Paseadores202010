import { Mascota } from '../mascota/mascota';
import { Tarjeta } from '../tarjeta/tarjeta';

export class Usuario {
  id: number;
  soyAdmin:boolean;
  nombre: string;
  edad: number;
  direccion: string;
  ciudad: string;
  contrasenia: string;
  fotoPerfil: string;
  telefono: number;
  correo: string;
  identificacion: number;
  nombreUsuario: string;
  mascotas: Array<Mascota>;
  tarjetas: Array<Tarjeta>;

  constructor(
    id: number,
    soyAdmin:boolean,
    nombre: string,
    edad: number,
    direccion: string,
    ciudad: string,
    contrasenia: string,
    fotoPerfil: string,
    telefono: number,
    correo: string,
    identificacion: number,
    nombreUsuario: string,
    mascotas: Array<Mascota>,
    tarjetas: Array<Tarjeta>) {
    this.id = id;
    this.nombre = nombre;
    this.edad = edad;
    this.direccion = direccion;
    this.ciudad = ciudad;
    this.contrasenia = contrasenia;
    this.fotoPerfil = fotoPerfil;
    this.telefono = telefono;
    this.correo = correo;
    this.soyAdmin = soyAdmin;
    this.identificacion = identificacion;
    this.nombreUsuario = nombreUsuario;
    this.tarjetas = tarjetas;
    this.mascotas = mascotas;
  }
}

