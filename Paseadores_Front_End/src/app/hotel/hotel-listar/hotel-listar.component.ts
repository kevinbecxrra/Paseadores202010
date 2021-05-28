import { Component, OnInit } from '@angular/core';
import { HotelService } from '../hotel.service';
import { Hotel } from '../hotel'

@Component({
  selector: 'app-hotel-listar',
  templateUrl: './hotel-listar.component.html',
  styleUrls: ['./hotel-listar.component.css']
})
export class HotelListarComponent implements OnInit {

  hotels : Array<Hotel>;
  hotelSeleccionado : Hotel;
  selected = false;

  constructor(private hotelService: HotelService) { }

  onSelected(h: Hotel){
    this.hotelSeleccionado = h;
    this.selected = true;
  }

  getHotels(): void {
    this.hotelService.getHotels()
      .subscribe(hotels => {
        this.hotels = hotels;
      });
  }

  ngOnInit() {
    this.getHotels();
  }

}
