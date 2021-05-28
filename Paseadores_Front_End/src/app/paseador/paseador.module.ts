import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {PaseadorListarComponent} from './paseador-listar/paseador-listar.component';
import {PaseadorDetalleComponent} from './paseador-detalle/paseador-detalle.component';
import{PaseadorCrearComponent} from './paseador-crear/paseador-crear.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PaseadorRoutingModule } from './paseadorRoutingModule.module';
import {PaseadorLoguearComponent} from './paseador-loguear/paseador-loguear.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    PaseadorRoutingModule
  ],
  declarations: [PaseadorListarComponent, PaseadorDetalleComponent,PaseadorCrearComponent, PaseadorLoguearComponent],
  exports: [PaseadorListarComponent],
})
export class PaseadorModule { }
