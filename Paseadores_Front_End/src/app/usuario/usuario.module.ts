import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioListarComponent } from './usuario-listar/usuario-listar.component';
import { UsuarioDetalleComponent } from './usuario-detalle/usuario-detalle.component';
import { UsuarioCrearComponent } from './usuario-crear/usuario-crear.component';
import { UsuarioLoginComponent } from './usuario-login/usuario-login.component';
import { TarjetaModule } from '../tarjeta/tarjeta.module';
import { MascotaModule } from '../mascota/mascota.module';
import { ReactiveFormsModule } from '@angular/forms';
import { UsuarioRoutingModule } from './UsuarioRoutingModule.module';
import { UsuarioPreLoginComponent } from './usuario-preLogin/usuario-preLogin.component';

@NgModule({
  imports: [
    CommonModule,
    TarjetaModule,
    MascotaModule,
    ReactiveFormsModule,
    UsuarioRoutingModule
  ],
  declarations: [UsuarioListarComponent,UsuarioDetalleComponent, UsuarioCrearComponent,UsuarioLoginComponent,UsuarioPreLoginComponent],
  exports: [UsuarioListarComponent,UsuarioCrearComponent],
})
export class UsuarioModule { }
