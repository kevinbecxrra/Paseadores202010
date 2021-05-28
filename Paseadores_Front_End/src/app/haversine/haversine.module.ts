import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HaversineListarComponent } from './haversine-listar/haversine-listar.component';
import { HaversineDetailComponent } from './haversine-detail/haversine-detail.component';
import { HaversineCreateComponent } from './haversine-create/haversine-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HaversineRoutingModule } from './HaversineRouting.module';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HaversineRoutingModule
  ],
  declarations: [HaversineListarComponent, HaversineDetailComponent, HaversineCreateComponent],
  exports: [HaversineListarComponent, HaversineCreateComponent]
})
export class HaversineModule { }
