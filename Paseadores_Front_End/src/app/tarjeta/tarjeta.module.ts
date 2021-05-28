import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TarjetaRoutingModule } from './TarjetaRoutingModule.module';
import { TarjetaListarComponent } from './tarjeta-listar/tarjeta-listar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TarjetaDetalleComponent } from './tarjeta-detalle/tarjeta-detalle.component';
import { TarjetaCrearComponent } from './tarjeta-crear/tarjeta-crear.component';


@NgModule({
  imports: [
    CommonModule,
    TarjetaRoutingModule,
    ReactiveFormsModule,
  ],
  declarations: [TarjetaListarComponent, TarjetaDetalleComponent,TarjetaCrearComponent],
  exports: [TarjetaListarComponent, TarjetaDetalleComponent,TarjetaCrearComponent]
})
export class TarjetaModule { }
