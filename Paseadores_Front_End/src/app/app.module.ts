
import { FacturaModule } from './factura/factura.module';
import { ReservaModule } from './reserva/reserva.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MascotaModule } from './mascota/mascota.module';
import { UsuarioModule } from './usuario/usuario.module';
import { TarjetaModule } from './tarjeta/tarjeta.module';
import { PaseadorModule } from './paseador/paseador.module';
import { DisponibilidadModule } from './disponibilidad/disponibilidad.module';
import { PaseoModule } from './paseo/paseo.module';
import { RatingModule } from './rating/rating.module';
import { HaversineModule } from './haversine/haversine.module';
import { HotelModule } from './hotel/hotel.module';
import { AppRouting } from './app-routing';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import {HomeModule} from './home/home.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ConocenosModule} from './conocenos/conocenos.module';

@NgModule({
  declarations: [
    AppComponent
   ],
  imports: [
    BrowserModule,
    ToastrModule.forRoot(),
    AppRouting,
    HttpClientModule,
    ConocenosModule,
    MascotaModule,
    BrowserAnimationsModule,
    UsuarioModule,
    TarjetaModule,
    HomeModule,
    PaseadorModule,
    DisponibilidadModule,
    PaseoModule,
    RatingModule,
    ReservaModule,
    FacturaModule,
    HaversineModule,
    HotelModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
