import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RatingListarComponent } from './rating-listar/rating-listar.component';
import { RatingDetailComponent } from './rating-detail/rating-detail.component';
import {RatingCreateComponent} from './rating-create/rating-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RatingRoutingModule } from './rating-routing.module';

@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, RatingRoutingModule
  ],
  declarations: [RatingListarComponent, RatingDetailComponent, RatingCreateComponent],
  exports: [RatingListarComponent, RatingCreateComponent]
})
export class RatingModule { }
