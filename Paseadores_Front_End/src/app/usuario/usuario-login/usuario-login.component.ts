import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-usuario-login',
  templateUrl: './usuario-login.component.html',
  styleUrls: ['./usuario-login.component.css']
})
export class UsuarioLoginComponent implements OnInit {

  usuarioForm: FormGroup;

  usuarios: Array<Usuario>;
  habilitado: boolean;

  constructor(private usuarioService: UsuarioService, private formBuilder: FormBuilder, private toastr: ToastrService
  ) {
    this.usuarios = [];
    this.habilitado = localStorage.getItem("tipoUsuario")===null?true:false;
  }

  ngOnInit() {

    this.usuarioService.getUsuarios().subscribe(usuarios => {
      this.usuarios = (usuarios);
    });

    this.usuarioForm = this.formBuilder.group({

      nombreUsuario: ["", [Validators.required, Validators.minLength(2)]],
      contrasenia: ["", [Validators.required, Validators.minLength(8)]],

    });

  }

  loginUsuario(newUsuario: Usuario) {
    // Process checkout data here
    let user = this.estaRegistrado(newUsuario);
    if (user!=null) {
      if(user.soyAdmin ){

      localStorage.setItem("administrador", JSON.stringify(user));
      localStorage.setItem("tipoUsuario", "administrador") ;
      }else{
        localStorage.setItem("cliente", JSON.stringify(user));
        localStorage.setItem("tipoUsuario", "cliente") ;
      }
      this.showSuccess(newUsuario);

      console.warn("Se inició sesión", newUsuario);

      this.usuarioForm.reset();
      this.cerrarForum();
    } else {
      this.showNotSuccess(newUsuario);
      this.cancelCreation();
    }

  }

  cerrarForum(){
    this.habilitado=false;
  }

  estaRegistrado(usuario: Usuario): Usuario {
    let respuesta = null;
    for (let a of this.usuarios) {
      respuesta = a.nombreUsuario === usuario.nombreUsuario && a.contrasenia === usuario.contrasenia ? a : respuesta;
    }
    return respuesta;
  }


  showSuccess(c: Usuario) {
    this.toastr.success('Has iniciado sesión exitosamente!', `Usuario ${c.nombreUsuario}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.usuarioForm.reset();
  }

  showNotSuccess(c: Usuario) {
    this.toastr.error('El nombre de usuario o la constraseña no son validos', `Usuario ${c.nombreUsuario}`, { "progressBar": true, timeOut: 4000 });
  }



}
