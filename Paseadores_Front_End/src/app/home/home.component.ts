import { Component, OnInit } from '@angular/core';
import { Paseo } from '../paseo/paseo';
import { PaseoService } from '../paseo/paseo.service'
import { Paseador } from '../paseador/paseador';
import{PaseadorService} from'../paseador/paseador.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  paseos: Array<Paseo>;
  paseadores:Array<Paseador>;
  selected=false;

  constructor(private paseoService: PaseoService, private paseadorService:PaseadorService) { }


  ngOnInit() {
    this.getPaseos();
    this.getPaseadores();
  }
  getPaseos(): void{
   this.paseoService.getPaseos().subscribe(paseos=>{
     this.paseos = paseos;
   })
  }
  getPaseadores(): void{
    this.paseadorService.getPaseadores().subscribe(paseadores=>{
      this.paseadores = paseadores;
    })
   }

}
