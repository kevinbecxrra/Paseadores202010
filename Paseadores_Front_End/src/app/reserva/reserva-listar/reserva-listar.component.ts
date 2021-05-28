import { ReservaService } from './../reserva.service';
import { Reserva } from './../reserva';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reserva-listar',
  templateUrl: './reserva-listar.component.html',
  styleUrls: ['./reserva-listar.component.css']
})
export class ReservaListarComponent implements OnInit {
    reservas: Array<Reserva>;
    reservaSeleccionada: Reserva;
    selected = false;
    constructor(private reservaService: ReservaService) { }
    getReservas(): void{
      this.reservaService.getReservas().subscribe(r => {
        this.reservas = r;
      });
    }
    onSelected(r: Reserva){
      this.reservaSeleccionada = r;
      this.selected = true;
    }
    ngOnInit() {
      this.getReservas();
    }

}
