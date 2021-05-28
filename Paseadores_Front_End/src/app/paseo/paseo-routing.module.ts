import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PaseoListarComponent } from './paseo-listar/paseo-listar.component';
import { PaseoCreateComponent } from './paseo-create/paseo-create.component';
import { PaseoDetailComponent } from './paseo-detail/paseo-detail.component';


const routes: Routes = [{
  path: 'paseos',
  children: [
    {
      path: 'list',
      component: PaseoListarComponent,
      pathMatch:'full'
    },
    {
      path: 'crear',
      children: [
        {
          path: ':idPaseador',
          component: PaseoCreateComponent,
        },
      ]
    },
    {
      path: ':id',
      component: PaseoDetailComponent,
      runGuardsAndResolvers: 'always'
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaseoRoutingModule { }
