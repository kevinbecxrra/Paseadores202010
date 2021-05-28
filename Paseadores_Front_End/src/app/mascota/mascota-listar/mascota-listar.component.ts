import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Mascota } from '../mascota';

import { MascotaService } from '../mascota.service'

@Component({
  selector: 'app-mascota-listar',
  templateUrl: './mascota-listar.component.html',
  styleUrls: ['./mascota-listar.component.css']
})
export class MascotaListarComponent implements OnInit {

  mascotas: Array<Mascota>;

  usuarioId: number;


  constructor(private route: ActivatedRoute,
    private router: Router, private mascotaService: MascotaService) {
    this.mascotas = [];


  }



  ngOnInit() {
    this.usuarioId = +this.route.snapshot.paramMap.get('usuario');

    this.getMascotas();

    this.getMascotasUsuario();

  }

  getMascotas(): void {
    this.mascotaService.getMascotas().subscribe(mascotas => {
      this.mascotas = mascotas;
    })
  }

  getMascotasUsuario() {

    if (this.usuarioId != null && !(this.usuarioId === undefined)) {
      let masc: Array<Mascota>;
      masc = [];
      for (let m of this.mascotas) {

        if (m.usuario.id == this.usuarioId) {
          masc.push(m);

        }
      }
      return masc;

    }
    else{
      return this.mascotas;
    }
  }


  darTipoUsuario(){
    return localStorage.getItem("tipoUsuario");
  }


}
