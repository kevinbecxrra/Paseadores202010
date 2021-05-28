import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Paseador } from '../paseador';
import { ActivatedRoute, Router } from '@angular/router';
import { PaseadorService } from '../paseador.service';
import { DisponibilidadService } from 'src/app/disponibilidad/disponibilidad.service';
import { Disponibilidad } from 'src/app/disponibilidad/disponibilidad';

@Component({
  selector: 'app-paseador-detalle',
  templateUrl: './paseador-detalle.component.html',
  styleUrls: ['./paseador-detalle.component.css']
})
export class PaseadorDetalleComponent implements OnInit {
   paseadorId:number;
   disponibilidades:Array<Disponibilidad>;
  @Input() paseadorDetail:Paseador;
  constructor(private route: ActivatedRoute,
    private router: Router, private paseadorService:PaseadorService, private disponibilidadService:DisponibilidadService,) { }

    ngOnInit() {
      if (this.paseadorDetail === undefined) {
        console.log('routerLink');
        this.paseadorId = +this.route.snapshot.paramMap.get('id');
        this.getPaseadorDetail();
        this.getDisponibilidades();
      } else { console.log(this.paseadorDetail.id); }
    }
    getDisponibilidades()  {
      this.disponibilidadService.getDisponibilidades(this.paseadorId)
        .subscribe(disponibilidades => {
          this.disponibilidades = disponibilidades;
          console.warn("aparecio",disponibilidades);
        });

  }
    getPaseadorDetail(): void {
      this.paseadorService.getPaseadorDetail(this.paseadorId)
        .subscribe(paseadorDetail => {
          this.paseadorDetail = paseadorDetail;
          console.warn("hola",paseadorDetail);
        });
    }
    deleteDisponibilidad( idDisp:number):void{
      this.disponibilidadService.deleteDisponibilidad(this.paseadorId,idDisp).subscribe();
      console.warn("hey",idDisp);
      console.warn("idPaseador",this.paseadorId);
    }
    refresh(): void {
      window.location.reload();
}
    darTipoUsuario(){
      return localStorage.getItem("tipoUsuario");
    }
    soyPaseador(){
      return localStorage.getItem("paseador");
    }
    soyAdmin(){
      return localStorage.getItem("administrador");
    }
}
