
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Paseador } from '../paseador';
import { PaseadorService } from '../paseador.service';
@Component({
  selector: 'app-paseador-crear',
  templateUrl: './paseador-crear.component.html',
  styleUrls: ['./paseador-crear.component.css']
})
export class PaseadorCrearComponent implements OnInit {
  paseadorForm:FormGroup;
  paseadores: Paseador[];
  constructor(private paseadorService: PaseadorService, private formBuilder:FormBuilder,private toastr:ToastrService) { }
  ngOnInit() {
    this.paseadorForm = this.formBuilder.group({
      id: ["", [Validators.required, Validators.minLength(3)]],
      nombre: ["", [Validators.required, Validators.minLength(2)]],
      correo: ["", [Validators.required, Validators.email]],
      identificacion: ["", [Validators.required,Validators.minLength(4)]],
      infoEPS: ["", [Validators.required, Validators.minLength(2)]],
      ARL: ["", [Validators.required, Validators.minLength(2)]],
      telefono: ["", [Validators.required, Validators.minLength(7)]],
      costoPaseo:["",[Validators.required, Validators.minLength(2)]]
    });

  }

  createPaseador(newPaseador: Paseador) {
    // Process checkout data here
    console.warn("el paseador fue creado", newPaseador);
    this.showSuccess(newPaseador);

    this.paseadorService.createPaseador(newPaseador).subscribe(paseador => {
    this.paseadores.push(newPaseador);
     this.showSuccess(newPaseador);
    });

    this.paseadorForm.reset();

  }

  showSuccess(c: Paseador) {
    this.toastr.success('Creado exitosamente!', `Paseador ${c.nombre}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.paseadorForm.reset();
  }


}
