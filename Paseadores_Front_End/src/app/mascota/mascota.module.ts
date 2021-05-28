import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MascotaListarComponent } from './mascota-listar/mascota-listar.component';
import { MascotaDetalleComponent } from './mascota-detalle/mascota-detalle.component';
import { MascotaCrearComponent } from './mascota-crear/mascota-crear.component'
import { ReactiveFormsModule } from '@angular/forms';
import { MascotaRoutingModule } from './MascotaRoutingModule.module';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MascotaRoutingModule

  ],
  declarations: [MascotaListarComponent, MascotaDetalleComponent, MascotaCrearComponent],
  exports: [MascotaListarComponent,MascotaDetalleComponent, MascotaCrearComponent]
})
export class MascotaModule { }
