import { Component, OnInit, Input } from '@angular/core';
import { Hotel } from '../hotel';

@Component({
  selector: 'app-hotel-detail',
  templateUrl: './hotel-detail.component.html',
  styleUrls: ['./hotel-detail.component.css']
})
export class HotelDetailComponent implements OnInit {
  @Input() hotelDetail : Hotel;
  constructor() { }

  ngOnInit() {
  }

}
