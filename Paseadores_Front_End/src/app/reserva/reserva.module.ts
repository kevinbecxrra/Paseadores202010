import { ReservaRoutingModule } from './reserva-routing';
import { ReservaCreateComponent } from './reserva-create/reserva-create.component';
import { ReservaDetalleComponent } from './reserva-detalle/reserva-detalle.component';
import { ReservaListarComponent } from './reserva-listar/reserva-listar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    ReservaRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ReservaListarComponent,ReservaDetalleComponent,ReservaCreateComponent],
  exports:[ReservaListarComponent,ReservaDetalleComponent,ReservaCreateComponent]
})
export class ReservaModule { }
