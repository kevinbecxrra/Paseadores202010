import { Component, OnInit, Input } from '@angular/core';
import { Haversine } from '../haversine';

@Component({
  selector: 'app-haversine-detail',
  templateUrl: './haversine-detail.component.html',
  styleUrls: ['./haversine-detail.component.css']
})
export class HaversineDetailComponent implements OnInit {
  @Input() haversineDetail: Haversine;
  constructor() { }

  ngOnInit() {
  }

}
