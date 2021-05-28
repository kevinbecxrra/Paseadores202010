import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TarjetaListarComponent } from './tarjeta-listar/tarjeta-listar.component';
//import { TarjetaCrearComponent } from './tarjeta-crear/tarjeta-crear.component';


const routes: Routes = [{
  path: 'tarjetas',
  children: [

    {
      path: ':idUsuario',
      component: TarjetaListarComponent
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TarjetaRoutingModule { }
