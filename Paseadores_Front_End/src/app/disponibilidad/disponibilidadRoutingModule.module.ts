import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DisponibilidadListarComponent } from './disponibilidad-listar/disponibilidad-listar.component';
import { DisponibilidadDetalleComponent } from './disponibilidad-detalle/disponibilidad-detalle.component';
import { DisponibilidadCrearComponent } from './disponibilidad-crear/disponibilidad-crear.component';


const routes: Routes = [{
  path: 'disponibilidades',
  children: [
     {
      path: 'crear',
      children: [
        {
          path: 'idPaseador',
          children:[{path: ':id',
                    component: DisponibilidadCrearComponent,}],
        },
      ]
     },
    {
      path: ':id',
      component: DisponibilidadDetalleComponent
    },
    {
      path: ':list',
      component: DisponibilidadListarComponent
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DisponibilidadRoutingModule { }
