import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuario } from '../usuario'
import { UsuarioService } from '../usuario.service';

@Component({
  selector: 'app-usuario-detalle',
  templateUrl: './usuario-detalle.component.html',
  styleUrls: ['./usuario-detalle.component.css']
})
export class UsuarioDetalleComponent implements OnInit {
  @Input() usuarioDetail: Usuario;
  usuarioId: number;
  abrirF:boolean;
  constructor( private route: ActivatedRoute,
    private router: Router, private usuarioService:UsuarioService) {

      this.abrirF=false;
   }

  ngOnInit() {
    if (this.usuarioDetail === undefined) {
      console.log('routerLink');
      this.usuarioId = +this.route.snapshot.paramMap.get('id');
      this.getUsuarioDetail();

    } else { console.log(this.usuarioDetail.id); }
  }

  abrir(){
    this.abrirF= !this.abrirF;
  }

  getUsuarioDetail(): void {
    this.usuarioService.getUsuarioDetail(this.usuarioId)
      .subscribe(usuarioDetail => {
        this.usuarioDetail = usuarioDetail;
      });

  }

  getTarjetas(){
    this.usuarioService.getTarjetas(this.usuarioId)
    .subscribe(tarjetasDetail => {
      this.usuarioDetail.tarjetas = tarjetasDetail;
    });
  }




}
