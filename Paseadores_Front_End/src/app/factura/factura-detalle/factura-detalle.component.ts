import { Factura } from './../factura';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-factura-detalle',
  templateUrl: './factura-detalle.component.html',
  styleUrls: ['./factura-detalle.component.css']
})
export class FacturaDetalleComponent implements OnInit {

  @Input() facturaDetail: Factura;
  constructor() { }

  ngOnInit() {
  }

}
