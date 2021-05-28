import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RatingListarComponent } from './rating-listar/rating-listar.component';
import { RatingCreateComponent } from './rating-create/rating-create.component';
import { RatingDetailComponent } from './rating-detail/rating-detail.component';

const routes: Routes = [{
  path: 'ratings',
  children: [
    {
      path: 'list',
      component: RatingListarComponent,
      pathMatch:'full'
    },
    {
      path: 'crear',
      children: [
        {
          path: 'idPaseador',
          children:[{path: ':idPaseador',
                    component: RatingCreateComponent,}],
        },
        {
          path: 'idPaseo',
          children:[{path: ':id',
                    component: RatingCreateComponent,}],
        },
      ]
    },
    {
      path: ':id',
      component: RatingDetailComponent
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class RatingRoutingModule { }
