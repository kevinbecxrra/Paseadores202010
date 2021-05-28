import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { Disponibilidad } from '../disponibilidad';
import {DisponibilidadService } from '../disponibilidad.service';

@Component({
  selector: 'app-disponibilidad-crear',
  templateUrl: './disponibilidad-crear.component.html',
  styleUrls: ['./disponibilidad-crear.component.css']
})
export class DisponibilidadCrearComponent implements OnInit {


  disponibilidadForm: FormGroup;
  disponibilidades: Disponibilidad[];
  disponibilidads: Disponibilidad[];
  usuarioId: number;

  constructor(private route: ActivatedRoute,
    private router: Router,private formBuilder: FormBuilder, private toastr: ToastrService, private disponibilidadService: DisponibilidadService
    ) {
       this.disponibilidads = [];
   }

  ngOnInit() {
    console.log('routerLink');
    this.usuarioId = +this.route.snapshot.paramMap.get('id');

    this.disponibilidadForm = this.formBuilder.group({
      fechaInicio: ["",Validators.required],
      duracion: ["", Validators.required],

    });

  }

  createDisponibilidad(newDisponibilidad: Disponibilidad) {
    // Process checkout data here
    console.warn("el disponibilidad fue creado", newDisponibilidad);

  this.disponibilidadService.createDisponibilidad(newDisponibilidad,this.usuarioId).subscribe(disponibilidad => {
    this.disponibilidads.push(newDisponibilidad);
    });
    this.showSuccess(newDisponibilidad);

    this.disponibilidadForm.reset();

  }

  showSuccess(c: Disponibilidad) {
    this.toastr.success('Se creó la disponibilidad exitosamente!', `Disponibilidad ${c.id}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.disponibilidadForm.reset();
  }
  getDisponibilidades()  {
    this.disponibilidadService.getDisponibilidades(this.usuarioId)
      .subscribe(disponibilidades => {
        this.disponibilidades = disponibilidades;
        return disponibilidades;
      });

    }
}
