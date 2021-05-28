import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { Paseo } from '../paseo';
import { PaseoService } from '../paseo.service';


@Component({
  selector: "app-paseo-create",
  templateUrl: "./paseo-create.component.html",
  styleUrls: ["./paseo-create.component.css"]
})
export class PaseoCreateComponent implements OnInit {
  paseoForm: FormGroup;

  paseos: Paseo[];
  paseadorId:number;

  constructor(private route: ActivatedRoute,
    private router: Router,private formBuilder: FormBuilder, private toastr: ToastrService, private paseoService: PaseoService
    ) {
    this.paseos = [];
  }

  createPaseo(newPaseo: Paseo) {
    // Process checkout data here
    console.warn("el paseo fue creado", newPaseo);
    this.showSuccess(newPaseo);
    newPaseo.paseador=JSON.parse(`{"id": ` + this.paseadorId + '}');
    this.paseoService.createPaseo(newPaseo).subscribe(paseo => {
    this.paseos.push(paseo);
    this.showSuccess(newPaseo);
    });this.paseoForm.reset();}

  showSuccess(c: Paseo) {
  this.toastr.success('Creado exitosamente!', `Paseo ${c.duracion}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.paseoForm.reset();
  }

  ngOnInit()
  {
    console.log('routerLink');
    this.paseadorId = +this.route.snapshot.paramMap.get('idPaseador');

    this.paseoForm = this.formBuilder.group({
      duracion: ["", Validators.required],
      fechaHora: ["", Validators.required],
      cupoMax: ["",Validators.required],
      descuento: ["",Validators.required],
      lleno:[false],
      completado:[false],
      ratingProm:[0]
    });
  }
}
