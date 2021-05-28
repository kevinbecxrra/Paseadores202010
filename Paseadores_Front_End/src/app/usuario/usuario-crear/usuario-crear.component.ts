import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-usuario-crear',
  templateUrl: './usuario-crear.component.html',
  styleUrls: ['./usuario-crear.component.css']
})
export class UsuarioCrearComponent implements OnInit {

  usuarioForm: FormGroup;

  usuarios: Usuario[];

  constructor(private usuarioService: UsuarioService,private formBuilder: FormBuilder, private toastr: ToastrService
    ) {
      this.usuarios = new Array<Usuario>();
   }

  ngOnInit() {
    this.usuarioForm = this.formBuilder.group({
      fotoPerfil: [""],
      nombreUsuario: ["", [Validators.required, Validators.minLength(2)]],
      nombre: ["", [Validators.required, Validators.minLength(2)]],
      correo: ["", [Validators.required, Validators.email]],
      identificacion: ["", [Validators.required,Validators.minLength(4)]],
      contrasenia: ["", [Validators.required, Validators.minLength(8)]],
      edad: ["", [Validators.required, Validators.min(18)]],
      direccion:["", [Validators.required, Validators.minLength(2)]],
      ciudad: ["", [Validators.required, Validators.minLength(2)]],
      telefono: ["", [Validators.required, Validators.minLength(7)]],
    });

  }

  createUsuario(newUsuario: Usuario) {
    // Process checkout data here
    console.warn("el usuario fue creado", newUsuario);
    if(newUsuario.fotoPerfil===undefined||newUsuario.fotoPerfil==null){
      newUsuario.fotoPerfil = "https://media.istockphoto.com/vectors/default-avatar-profile-icon-grey-photo-placeholder-vector-id1018999828";
    }
    this.showSuccess(newUsuario);

    this.usuarioService.createUsuario(newUsuario).subscribe(usuario => {
    this.usuarios.push(newUsuario);
     this.showSuccess(newUsuario);
    });

    this.usuarioForm.reset();

  }

  showSuccess(c: Usuario) {
    this.toastr.success('Creado exitosamente!', `Usuario ${c.nombreUsuario}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.usuarioForm.reset();
  }


}
