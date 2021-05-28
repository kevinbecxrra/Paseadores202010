import { Reserva } from './../reserva';
import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: 'app-create-Reserva',
  templateUrl: './reserva-create.component.html',
  styleUrls: ['./reserva-create.component.css']
})
export class ReservaCreateComponent implements OnInit {

  fechaPatternCheck = /(((0[1-9]|1[012])-[0-2]\d)|((0[1,3-9]|1[012])-30)|((0?[1,3,5,7,8]|1[02])-31))-\d{4}\s(0?[1-9]|1\d|2[0-3])(:([0-5]\d)){2}/;
  fechaPattern = /\d{1,2}-\d{1,2}-\d{4}\s\d{1,2}:\d{1,2}:\d{1,2}/;
  tipoPattern = /[(H|h)]{1}[o]{1}[t]{1}[e]{1}[l]{1}|[(P|p)]{1}[a]{1}[s]{1}[e]{1}[o]{1}/;
  reservaForm: FormGroup;
  constructor(private formBuilder:FormBuilder) { }

  ngOnInit() {
    this.reservaForm = this.formBuilder.group({
      fechaHora: ["",[Validators.required,Validators.pattern(this.fechaPattern)]],
      tipo: ["",[Validators.required,Validators.pattern(this.tipoPattern)]],
      diasEstadia : ["",[Validators.required,Validators.min(0)]]
    });
  }
  cancelCreate(){
    console.log("Cancelando");
    this.reservaForm.reset();
  }

}

