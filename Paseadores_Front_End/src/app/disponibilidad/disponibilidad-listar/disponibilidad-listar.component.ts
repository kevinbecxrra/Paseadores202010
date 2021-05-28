import { Component, OnInit } from '@angular/core';
import { Disponibilidad } from '../disponibilidad';
import { DisponibilidadService } from '../disponibilidad.service';
@Component({
  selector: 'app-disponibilidad-listar',
  templateUrl: './disponibilidad-listar.component.html',
  styleUrls: ['./disponibilidad-listar.component.css']
})
export class DisponibilidadListarComponent implements OnInit {
 disponibilidades: Array<Disponibilidad>;
 disponibilidadSeleccionada : Disponibilidad;
 selected = false;
  constructor(private disponibilidadService: DisponibilidadService) { }

  ngOnInit() {
  }
  onSelected(d: Disponibilidad): void {
    this.selected = true;
    this.disponibilidadSeleccionada = d;
  }

}
