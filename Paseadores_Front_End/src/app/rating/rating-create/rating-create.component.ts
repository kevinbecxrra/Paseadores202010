import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from "ngx-toastr";
import { Rating } from '../rating';
import { RatingService } from '../rating.service';


@Component({
  selector: "app-rating-create",
  templateUrl: "./rating-create.component.html",
  styleUrls: ["./rating-create.component.css"]
})
export class RatingCreateComponent implements OnInit {
  ratingForm: FormGroup;
  ratings: Rating[];
  paseoId:number;
  paseadorId:number;

  constructor(private route: ActivatedRoute,
    private router: Router,private formBuilder: FormBuilder, private toastr: ToastrService, private ratingService: RatingService
    ) {
    this.ratings = [];
  }

  createRating(newRating: Rating) {
    // Process checkout data here
    console.warn("el rating fue creado", newRating);
    console.warn("paseador", this.paseadorId);
    console.warn("paseo", this.paseoId);

    if(this.paseoId!=0){
    this.showSuccess(newRating);
    newRating.paseo=JSON.parse(`{"id": ` + this.paseoId + '}');}

    if(this.paseadorId!=0){
    this.showSuccess(newRating);
    newRating.paseador=JSON.parse(`{"id": ` + this.paseadorId + '}');}

    this.ratingService.createRating(newRating).subscribe(rating => {
    this.ratings.push(rating);
    });
    //-----------------------------------------------------------------
    this.ratingForm.reset();
  }

  showSuccess(c: Rating) {
  this.toastr.success('Creado exitosamente!', `Rating ${c.estrellas}`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.ratingForm.reset();
  }

  ngOnInit() {
    console.log('routerLink');
    this.paseoId = +this.route.snapshot.paramMap.get('id');
    this.paseadorId = +this.route.snapshot.paramMap.get('idPaseador');
    this.ratingForm = this.formBuilder.group({
      estrellas: ["", Validators.required],
      comentario: ["", Validators.required]
    });
  }
}
