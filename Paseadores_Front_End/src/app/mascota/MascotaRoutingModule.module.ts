import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MascotaListarComponent } from './mascota-listar/mascota-listar.component';
import { MascotaDetalleComponent } from './mascota-detalle/mascota-detalle.component';
import { MascotaCrearComponent } from './mascota-crear/mascota-crear.component';


const routes: Routes = [{
  path: 'mascotas',
  children: [

    {
      path: 'list',
      component: MascotaListarComponent

    },
    {
      path: ':usuario',
      component: MascotaListarComponent
    },
    {
      path: 'detail',
      children: [
        {
          path: ':id',
          component: MascotaDetalleComponent
        },
      ]
    },
    {
      path: 'crear',
      children: [
        {
          path: ':idUsuario',
          component: MascotaCrearComponent
        },
      ]
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MascotaRoutingModule { }
