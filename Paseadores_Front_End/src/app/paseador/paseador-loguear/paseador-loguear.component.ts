import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Paseador } from '../paseador';
import { PaseadorService } from '../paseador.service';

@Component({
  selector: 'app-paseador-login',
  templateUrl: './paseador-loguear.component.html',
  styleUrls: ['./paseador-loguear.component.css']
})
export class PaseadorLoguearComponent implements OnInit {

  paseadorForm: FormGroup;

  paseadores: Array<Paseador>;
  habilitado: boolean;

  constructor(private paseadorService: PaseadorService, private formBuilder: FormBuilder, private toastr: ToastrService
  ) {
    this.paseadores = [];
    this.habilitado = localStorage.getItem("tipoUsuario")===null?true:false;
  }

  ngOnInit() {

    this.paseadorService.getPaseadores().subscribe(paseadores => {
      this.paseadores = paseadores;
    });

    this.paseadorForm = this.formBuilder.group({

      correo: ["", [Validators.required, Validators.minLength(2)]],
      identificacion: ["", [Validators.required, Validators.minLength(8)]],

    });

  }

  loginPaseador(newPaseador: Paseador) {
    // Process checkout data here
    let paseador = this.estaRegistrado(newPaseador);
    if (paseador!=null) {


      localStorage.setItem("paseador", JSON.stringify(paseador));
      localStorage.setItem("tipoUsuario", "paseador") ;


      this.showSuccess(paseador);

      console.warn("Se inició sesión", newPaseador);

      this.paseadorForm.reset();
      this.cerrarForum();
    } else {
      this.showNotSuccess(newPaseador);
      this.cancelCreation();
    }

  }

  cerrarForum(){
    this.habilitado=false;
  }

  estaRegistrado(paseador: Paseador): Paseador {
    let respuesta = null;
    for (let a of this.paseadores) {
      respuesta = a.correo === paseador.correo && a.identificacion == paseador.identificacion ? a : respuesta;
    }
    return respuesta;
  }


  showSuccess(c: Paseador) {
    this.toastr.success('Has iniciado sesión exitosamente!', `Paseador ${c.nombre}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.paseadorForm.reset();
  }

  showNotSuccess(c: Paseador) {
    this.toastr.error('El nombre de paseador o la constraseña no son validos', `Paseador con correo: ${c.correo}`, { "progressBar": true, timeOut: 4000 });
  }



}
