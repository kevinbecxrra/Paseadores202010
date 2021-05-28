import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaseadorListarComponent } from './paseador-listar/paseador-listar.component';
import { PaseadorDetalleComponent } from './paseador-detalle/paseador-detalle.component';
import { PaseadorLoguearComponent } from './paseador-loguear/paseador-loguear.component';
import { PaseadorCrearComponent } from './paseador-crear/paseador-crear.component';
const routes: Routes = [{
  path: 'paseadores',
  children: [
    {
      path: 'crear',
          component: PaseadorCrearComponent,
    },
    {
      path: 'login',
      component: PaseadorLoguearComponent
    },
    {
      path: 'list',
      component: PaseadorListarComponent
    },
    {
      path: ':id',
      component: PaseadorDetalleComponent
    },

  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaseadorRoutingModule { }
