import { FacturaRoutingModule } from './factura-routing.module';
import { FacturaCreateComponent } from './factura-crear/factura-crear.component';
import { FacturaDetalleComponent } from './factura-detalle/factura-detalle.component';
import { FacturaListarComponent } from './factura-listar/factura-listar.component';

import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FacturaRoutingModule
  ],
  declarations: [FacturaListarComponent,FacturaDetalleComponent,FacturaCreateComponent],
  exports: [FacturaListarComponent,FacturaDetalleComponent,FacturaCreateComponent]
})
export class FacturaModule { }
