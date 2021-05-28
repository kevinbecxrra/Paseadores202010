import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Mascota } from '../mascota'
import { MascotaService } from '../mascota.service';

@Component({
  selector: 'app-mascota-detalle',
  templateUrl: './mascota-detalle.component.html',
  styleUrls: ['./mascota-detalle.component.css']
})
export class MascotaDetalleComponent implements OnInit {
  @Input() mascotaDetail: Mascota;
  mascotaId: number;

  constructor( private route: ActivatedRoute,
    private router: Router, private mascotaService:MascotaService) {

   }

  ngOnInit() {
    if (this.mascotaDetail === undefined) {
      console.log('routerLink');
      this.mascotaId = +this.route.snapshot.paramMap.get('id');
      this.getMascotaDetail();

    } else { console.log(this.mascotaDetail.id); }
  }

  darTipoUsuario(){
    return localStorage.getItem("tipoUsuario");
  }

  getMascotaDetail(): void {
    this.mascotaService.getMascotaDetail(this.mascotaId)
      .subscribe(mascotaDetail => {
        this.mascotaDetail = mascotaDetail;
      });

  }




}
