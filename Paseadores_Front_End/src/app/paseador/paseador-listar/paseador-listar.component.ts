import { ToastrService } from 'ngx-toastr';
import { Component, OnInit } from '@angular/core';
import { Paseador } from '../paseador';
import { PaseadorService } from '../paseador.service';
import { Disponibilidad } from 'src/app/disponibilidad/disponibilidad';
@Component({
  selector: 'app-paseador-listar',
  templateUrl: './paseador-listar.component.html',
  styleUrls: ['./paseador-listar.component.css']
})
export class PaseadorListarComponent implements OnInit {
  paseadores: Array<Paseador>;
  filtrado: Array<Paseador>;
  paseadorSeleccionado : Paseador;
  disp: Array<Disponibilidad>;
  dispo: Disponibilidad;
  size:number;
  fechaRegex =  /\d{1,4}-\d{1,2}-\d{1,2}T\d{1,2}:\d{1,2}:\d{1,2}.\d{1,3}Z[[](UTC)[\]]/
  selected =false;
  constructor(private paseadorService: PaseadorService, private toastr: ToastrService
    ) { }

  ngOnInit() {
    this.getPaseadores();
  }
  onSelected(p: Paseador): void {
    this.selected = true;
    this.paseadorSeleccionado = p;
  }
  getPaseadores(): void {
    this.paseadorService.getPaseadores()
      .subscribe(paseadores => {
        this.paseadores = paseadores;
      });
    }
    refresh(): void {
      window.location.reload();
}
//retorna 5 y no 3 osea deberia retornar 3
    filtrarPaseadores(fecha:string){
      let borrar:Boolean = false;
      let mantener:Boolean =true;
     for (let index = 0; index <this.paseadores.length; index++) {
       const idx = index;
         borrar=true;
         mantener=false;
         for (let y = 0; y < this.paseadores[idx].disponibilidad.length; y++) {

                if(this.paseadores[idx].disponibilidad[y].fechaInicio!=fecha)
                {
                  borrar = true;
                }
                else{
                  mantener=true;
                  borrar=false;
                }
              }
              if(borrar==true && mantener==false)
              {
                console.warn("eliminado",this.paseadores[idx]);
                this.paseadores.splice(idx,1);
                index--;
              }

       }
       console.warn("paseadores",this.paseadores);

    }
    soyAdmin()
    {
      return localStorage.getItem("administrador");
    }

    getFilterValue(): string {
      let fechaFormatted: string = "";
      var fecha :  HTMLInputElement = <HTMLInputElement> document.getElementById("filtro-paseador")!;
      if (!fecha.value.match(this.fechaRegex))
        this.showNotSuccess(fecha.value);
      else {
        fechaFormatted = fecha.value;
        this.showSuccess(fechaFormatted);
      }
      return fechaFormatted;
    }
    showSuccess(c: string) {
      this.toastr.success('Se ha filtrado exitosamente!', `Hora de filtrado ${c}`, { "progressBar": true, timeOut: 4000 });
    }
    showNotSuccess(c: string) {
      this.toastr.error('El formato de la fecha es invalido', `Hora usada ${c}`, { "progressBar": true, timeOut: 4000 });
      this.getPaseadores();
    }
  }
