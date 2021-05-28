import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Haversine } from '../haversine';
import { HaversineService } from '../haversine.service';

@Component({
  selector: 'app-haversine-create',
  templateUrl: './haversine-create.component.html',
  styleUrls: ['./haversine-create.component.css']
})
export class HaversineCreateComponent implements OnInit {

  haversineForm: FormGroup;

  haversines: Haversine[];

  constructor(private haversineService: HaversineService,private formBuilder: FormBuilder, private toastr: ToastrService
    ) {
      this.haversines = new Array<Haversine>();
   }

   ngOnInit() {
    this.haversineForm = this.formBuilder.group({
      horaDePaso: ["", [Validators.required, Validators.minLength(5)]],
      longitud: ["", [Validators.required, Validators.minLength(5)]],
      latitud: ["", [Validators.required, Validators.minLength(5)]],
      //paseo: [0]
    });
  }

   createHaversine(newHaversine: Haversine) {
    // Process checkout data here
    console.warn("el haversine fue creado", newHaversine);
    this.showSuccess(newHaversine);

    this.haversineService.createHaversine(newHaversine).subscribe(haversine => {
    this.haversines.push(newHaversine);
     this.showSuccess(newHaversine);
    });

    this.haversineForm.reset();

  }

  showSuccess(c: Haversine) {
    this.toastr.success('Creado exitosamente!', `Haversine Paseadores`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.haversineForm.reset();
  }
}
