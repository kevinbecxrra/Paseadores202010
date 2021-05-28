import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HaversineListarComponent } from './haversine-listar/haversine-listar.component';
import { HaversineDetailComponent } from './haversine-detail/haversine-detail.component';
import { HaversineCreateComponent } from './haversine-create/haversine-create.component';

const routes: Routes = [{
  path: 'haversines',
  children: [
    {
      path: 'list',
      component: HaversineListarComponent
    },
    {
      path: 'new',
      component: HaversineCreateComponent
    },
    {
      path: ':id',
      component: HaversineDetailComponent
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HaversineRoutingModule { }
