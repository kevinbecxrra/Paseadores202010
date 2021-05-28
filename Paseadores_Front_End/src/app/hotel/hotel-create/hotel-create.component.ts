import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from "ngx-toastr";
import { Hotel } from '../hotel';
import { HotelService } from '../hotel.service';

@Component({
  selector: 'app-hotel-create',
  templateUrl: './hotel-create.component.html',
  styleUrls: ['./hotel-create.component.css']
})
export class HotelCreateComponent implements OnInit {

  hotelForm: FormGroup;

  hotels: Hotel[];

  constructor(private hotelService: HotelService,private formBuilder: FormBuilder, private toastr: ToastrService
    ) {
      this.hotels = new Array<Hotel>();
   }

  ngOnInit() {
    this.hotelForm = this.formBuilder.group({
      tarifaInicial: ["", [Validators.required, Validators.minLength(4)]],
      cupoMaximo: ["", [Validators.required, Validators.minLength(2)]],
      comidaMascota: [false],
      reservas: [0]
    });
  }

  createHotel(newHotel: Hotel) {
    // Process checkout data here
    console.warn("el hotel fue creado", newHotel);
    this.showSuccess(newHotel);

    this.hotelService.createHotel(newHotel).subscribe(hotel => {
    this.hotels.push(newHotel);
     this.showSuccess(newHotel);
    });

    this.hotelForm.reset();

  }

  showSuccess(c: Hotel) {
    this.toastr.success('Creado exitosamente!', `Hotel Paseadores`, { "progressBar": true, timeOut: 4000 });
  }

  cancelCreation() {
    console.log("Cancelando ...");
    this.hotelForm.reset();
  }
}
