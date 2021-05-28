import { Component, OnInit } from '@angular/core';
import { Tarjeta } from '../tarjeta';
import { TarjetaService } from '../tarjeta.service';
import { Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-tarjeta-listar',
  templateUrl: './tarjeta-listar.component.html',
  styleUrls: ['./tarjeta-listar.component.css']
})
export class TarjetaListarComponent implements OnInit {
  usuarioId: number;
  tarjetas:Array<Tarjeta>;

  tarjetaSeleccionada: Tarjeta;
  selected=false;
  constructor( private tarjetaService: TarjetaService ) { }

  ngOnInit() {

      //console.log('routerLink');
     // this.usuarioId = +this.route.snapshot.paramMap.get('idUsuario');
      this.getTarjetas();

  }
  onSelected(t: Tarjeta){
    this.selected =true;
    this.tarjetaSeleccionada = t;
  }

  getTarjetas(): void {
    this.tarjetaService.getTarjetas()
      .subscribe(tarjetas => {
        this.tarjetas = tarjetas;
      });

  }

}
