import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { DisponibilidadListarComponent } from './disponibilidad-listar/disponibilidad-listar.component';
import { DisponibilidadDetalleComponent } from "./disponibilidad-detalle/disponibilidad-detalle.component";
import{DisponibilidadRoutingModule} from"./disponibilidadRoutingModule.module"
import{DisponibilidadCrearComponent} from"./disponibilidad-crear/disponibilidad-crear.component"
@NgModule({
  imports: [
    CommonModule,
    DisponibilidadRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [DisponibilidadListarComponent,DisponibilidadDetalleComponent,DisponibilidadCrearComponent],
  exports: [DisponibilidadListarComponent],

})
export class DisponibilidadModule { }
