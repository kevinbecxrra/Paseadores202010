import { Component, OnInit, Input } from '@angular/core';
import { Reserva } from '../reserva';

@Component({
  selector: 'app-reserva-detalle',
  templateUrl: './reserva-detalle.component.html',
  styleUrls: ['./reserva-detalle.component.css']
})
export class ReservaDetalleComponent implements OnInit {

  @Input() reservaDetail: Reserva;
  constructor() { }

  ngOnInit() {
  }

}
