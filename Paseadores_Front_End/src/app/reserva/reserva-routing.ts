import { ReservaCreateComponent } from './reserva-create/reserva-create.component';
import { ReservaDetalleComponent } from './reserva-detalle/reserva-detalle.component';

import { ReservaListarComponent } from './reserva-listar/reserva-listar.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes:Routes = [
  {
  path: 'reservas',
  children: [
    {
      path: 'list',
      component: ReservaListarComponent

    },
    {
      path: ':crear',
      component: ReservaCreateComponent
    },
    {
      path: 'detail',
      children: [
        {
          path: ':idMascota',
          component: ReservaDetalleComponent
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
export class ReservaRoutingModule { }
