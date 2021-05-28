import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Disponibilidad } from '../disponibilidad';

@Component({
  selector: 'app-disponibilidad-detalle',
  templateUrl: './disponibilidad-detalle.component.html',
  styleUrls: ['./disponibilidad-detalle.component.css']
})
export class DisponibilidadDetalleComponent implements OnInit {
  @Input() disponibilidadDetail: Disponibilidad;
  constructor() { }

  ngOnInit() {
  }

}


