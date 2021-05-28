import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsuarioListarComponent } from './usuario-listar/usuario-listar.component';
import { UsuarioDetalleComponent } from './usuario-detalle/usuario-detalle.component';
import { UsuarioLoginComponent } from './usuario-login/usuario-login.component';
import { UsuarioPreLoginComponent } from './usuario-preLogin/usuario-preLogin.component';
import { UsuarioCrearComponent } from './usuario-crear/usuario-crear.component';


const routes: Routes = [{
  path: 'usuarios',
  children: [
    {
      path: 'crear',
      component: UsuarioCrearComponent
    },
    {
      path: 'preLogin',
      component: UsuarioPreLoginComponent
    },
    {
      path: 'login',
      component: UsuarioLoginComponent
    },
    {
      path: 'list',
      component: UsuarioListarComponent
    },
    {
      path: ':id',
      component: UsuarioDetalleComponent
    },

  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
