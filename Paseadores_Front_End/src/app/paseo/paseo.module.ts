import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaseoListarComponent } from './paseo-listar/paseo-listar.component';
import { PaseoDetailComponent } from './paseo-detail/paseo-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PaseoCreateComponent } from './paseo-create/paseo-create.component';
import { PaseoRoutingModule } from './paseo-routing.module';

@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule,  PaseoRoutingModule
  ],
  declarations: [PaseoListarComponent, PaseoDetailComponent, PaseoCreateComponent],
  exports: [PaseoListarComponent, PaseoCreateComponent]
})
export class PaseoModule { }
