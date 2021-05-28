import { Component } from '@angular/core';
import { Usuario } from './usuario/usuario';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'front';


   soyAdmin(): boolean{

   let admin = localStorage.getItem("administrador");
   return admin===undefined || admin===null? false:true;
  }

  soyCliente(): boolean{

    let c = localStorage.getItem("cliente");
    return c===undefined || c===null? false:true;
   }

   soyPaseador(): boolean{

    let p = localStorage.getItem("paseador");
    return p===undefined || p===null? false:true;
   }

  estaLogueado(): boolean{
    let logueado = localStorage.getItem("tipoUsuario");
    return logueado===undefined || logueado===null? false:true;
   }

   darUsuarioActual():Usuario{
    return JSON.parse(localStorage.getItem(localStorage.getItem("tipoUsuario")));
   }

   cerrarSesion(){
     localStorage.removeItem(localStorage.getItem("tipoUsuario"));
     localStorage.removeItem("tipoUsuario");
   }


}
