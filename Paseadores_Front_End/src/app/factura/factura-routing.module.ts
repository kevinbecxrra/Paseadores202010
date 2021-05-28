import { FacturaDetalleComponent } from './factura-detalle/factura-detalle.component';
import { FacturaCreateComponent } from './factura-crear/factura-crear.component';
import { FacturaListarComponent } from './factura-listar/factura-listar.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes:Routes = [
  {
  path: 'facturas',
  children: [
    {
      path: 'list',
      component: FacturaListarComponent

    },
    {
      path: ':crear',
      component: FacturaCreateComponent
    },
    {
      path: 'detail',
      children: [
        {
          path: ':id',
          component: FacturaDetalleComponent
        },
      ]
    }
  ]
  }
]

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FacturaRoutingModule { }
