import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Tarjeta } from '../tarjeta';
import {TarjetaService } from '../tarjeta.service';

@Component({
  selector: 'app-tarjeta-crear',
  templateUrl: './tarjeta-crear.component.html',
  styleUrls: ['./tarjeta-crear.component.scss']
})
export class TarjetaCrearComponent implements OnInit {

  tarjetaForm: FormGroup;
  tarjetas:Tarjeta[];
  @Input() idUsuario:number;
  constructor(private tarjetaService: TarjetaService,private formBuilder: FormBuilder, private toastr: ToastrService) {
    this.tarjetas=[];

  }

  ngOnInit() {
    this.tarjetaForm = this.formBuilder.group({
      numeroTarjeta: ["", [Validators.required, Validators.minLength(12)]],
      codigoSeguridad: ["", [Validators.required, Validators.minLength(3)]],
      tipo: ["", Validators.required],
      fechaVencimiento: ["", Validators.required]});
  }



  createTarjeta(newTarjeta: Tarjeta) {
    // Process checkout data here
    console.warn("el usuario fue creado", newTarjeta);


    this.tarjetaService.createTarjeta(newTarjeta,this.idUsuario).subscribe(usuario => {
    this.tarjetas.push(newTarjeta);
     this.showSuccess(newTarjeta);
    });

    this.tarjetaForm.reset();
  }
  showSuccess(c:Tarjeta) {
    this.toastr.success('Creado exitosamente!', `Tarjeta ${c.tipo}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.tarjetaForm.reset();
  }

}
