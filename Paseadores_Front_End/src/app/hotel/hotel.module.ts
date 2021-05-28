import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HotelListarComponent } from './hotel-listar/hotel-listar.component';
import { HotelDetailComponent } from './hotel-detail/hotel-detail.component';
import { HotelCreateComponent } from './hotel-create/hotel-create.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HotelRoutingModule } from './HotelRouting.module';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    HotelRoutingModule
  ],
  declarations: [HotelListarComponent, HotelDetailComponent, HotelCreateComponent],
  exports: [HotelListarComponent, HotelCreateComponent]
})
export class HotelModule {}
