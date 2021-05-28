import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { Usuario } from 'src/app/usuario/usuario';
import { Mascota } from '../mascota';
import {MascotaService } from '../mascota.service';

@Component({
  selector: 'app-mascota-crear',
  templateUrl: './mascota-crear.component.html',
  styleUrls: ['./mascota-crear.component.scss']
})
export class MascotaCrearComponent implements OnInit {


  mascotaForm: FormGroup;

  mascotas: Mascota[];
  usuarioId: number;

  constructor(private route: ActivatedRoute,
    private router: Router,private formBuilder: FormBuilder, private toastr: ToastrService, private mascotaService: MascotaService
    ) {
       this.mascotas = [];
   }

  ngOnInit() {
    console.log('routerLink');
    this.usuarioId = +this.route.snapshot.paramMap.get('idUsuario');

    this.mascotaForm = this.formBuilder.group({
      foto: [""],
      nombre: ["", Validators.required],
      raza: ["", Validators.required],
      color: ["", Validators.required],
      seniasParticulares: ["", Validators.required],
      historiaClinica: ["", Validators.required],
      edad: ["", Validators.required],
      sexo:[""]

    });

  }

  createMascota(newMascota: Mascota) {
    // Process checkout data here
    console.warn("el mascota fue creado", newMascota);
    if(newMascota.foto===undefined||newMascota.foto==null||newMascota.foto == ""){
      newMascota.foto = "https://e7.pngegg.com/pngimages/922/846/png-clipart-dog-computer-icons-pet-share-icon-dog-mammal-animals.png";
    }

    newMascota.usuario=JSON.parse(`{"id": ` + this.usuarioId + '}');
    this.mascotaService.createMascota(newMascota).subscribe(mascota => {
    this.mascotas.push(newMascota);
    });
    this.showSuccess(newMascota);

    this.mascotaForm.reset();

  }

  showSuccess(c: Mascota) {
    this.toastr.success('Se creó la mascota exitosamente!', `Mascota ${c.nombre}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.mascotaForm.reset();
  }



}
