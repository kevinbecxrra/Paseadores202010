import { Component, OnInit } from '@angular/core';
import { HaversineService } from '../haversine.service';
import { Haversine } from '../haversine'

@Component({
  selector: 'app-haversine-listar',
  templateUrl: './haversine-listar.component.html',
  styleUrls: ['./haversine-listar.component.css']
})
export class HaversineListarComponent implements OnInit {

  haversines : Array<Haversine>;
  haversineSeleccionada : Haversine;
  selected = false;

  constructor(private haversineService: HaversineService) { }

  onSelected(h: Haversine){
    this.haversineSeleccionada = h;
    this.selected = true;
  }

  getHaversines(): void {
    this.haversineService.getHaversines()
      .subscribe(haversines => {
        this.haversines = haversines;
      });
  }

  ngOnInit() {
    this.getHaversines();
  }

}
