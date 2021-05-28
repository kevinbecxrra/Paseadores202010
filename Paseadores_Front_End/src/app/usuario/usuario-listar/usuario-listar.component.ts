import { Component, OnInit } from '@angular/core';
import { Usuario } from '../usuario';
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-usuario-listar',
  templateUrl: './usuario-listar.component.html',
  styleUrls: ['./usuario-listar.component.css']
})
export class UsuarioListarComponent implements OnInit {
  usuarios: Array<Usuario>;
  usuarioSeleccionado: Usuario;
  selected = false;
  constructor(private usuarioService: UsuarioService) { }

  ngOnInit() {
    this.getUsuarios()
  }



  darTipoUsuario(){
    return localStorage.getItem("tipoUsuario");
  }

  getUsuarios(): void {
    this.usuarioService.getUsuarios()
      .subscribe(usuarios => {
        this.usuarios = usuarios;
      });

  }
}
