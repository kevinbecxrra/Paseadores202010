import { Component, OnInit } from '@angular/core';
import { Paseo } from '../paseo';
import { PaseoService } from '../paseo.service'

@Component({
  selector: 'app-paseo-listar',
  templateUrl: './paseo-listar.component.html',
  styleUrls: ['./paseo-listar.component.css']
})
export class PaseoListarComponent implements OnInit {

  paseos: Array<Paseo>;
  paseoSeleccionado: Paseo;
  selected=false;

  constructor(private paseoService: PaseoService) { }

  onSelected(m: Paseo){
    this.paseoSeleccionado = m;
    this.selected = true;
  }
  ngOnInit() {
    this.getPaseos();
  }
  getPaseos(): void{
   this.paseoService.getPaseos().subscribe(paseos=>{
     this.paseos = paseos;
   })
  }
  darTipoUsuario(){
    return localStorage.getItem("tipoUsuario");
  }

}
