import { FacturaService } from './../factura.service';
import { Factura } from './../factura';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-factura-listar',
  templateUrl: './factura-listar.component.html',
  styleUrls: ['./factura-listar.component.css']
})
export class FacturaListarComponent implements OnInit {
  facturas: Array<Factura>;
  facturaSeleccionada: Factura;
  selected = false;
  constructor(private facturaService: FacturaService) { }
  getFacturas(): void{
    this.facturaService.getFacturas().subscribe( f => {
      this.facturas = f;
    });
  }
  onSelected(f:Factura){
    this.selected = true;
    this.facturaSeleccionada = f;
  }
  ngOnInit() {
  this.getFacturas();
  }

}
