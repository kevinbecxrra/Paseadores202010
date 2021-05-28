import { Reserva } from './../../reserva/reserva';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ToastrService } from "ngx-toastr";


@Component({
  selector: 'app-create-Factura',
  templateUrl: './factura-crear.component.html',
  styleUrls: ['./factura-crear.component.css']
})
export class FacturaCreateComponent implements OnInit {

  private longref:number = 8;
  private impuesto:number = 19;
  private descuentoMax:number  = 50;
  private minimoCompra:number = 0;
  facturaForm:FormGroup;
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.facturaForm = this.formBuilder.group({
      referencia:["",[Validators.required,Validators.minLength(this.longref)]],
      concepto:["",[Validators.required]],
      descuento:["",[Validators.required,Validators.min(0),Validators.max(this.descuentoMax)]],
      descripcionCompra:["",[Validators.required]],
      totalCompra:["",[Validators.required,Validators.min(this.minimoCompra)]],
      impuesto:["",[Validators.required,Validators.min(0),Validators.max(this.impuesto)]],
      subtotal:["",[Validators.required,Validators.min(this.minimoCompra)]],
      diasEstadia:["",[Validators.required,Validators.min(0)]]
    });
  }

  cancelCreate(){
    console.log("Cancelando");
    this.facturaForm.reset();
  }
}
