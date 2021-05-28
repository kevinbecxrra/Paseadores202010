import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HotelListarComponent } from './hotel-listar/hotel-listar.component';
import { HotelDetailComponent } from './hotel-detail/hotel-detail.component';
import { HotelCreateComponent } from './hotel-create/hotel-create.component';


const routes: Routes = [{
  path: 'hotels',
  children: [
    {
      path: 'list',
      component: HotelListarComponent
    },
    {
      path: 'new',
      component: HotelCreateComponent
    },
    {
      path: ':id',
      component: HotelDetailComponent
    },
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HotelRoutingModule { }
