/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DisponibilidadListarComponent } from './disponibilidad-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";

import { Disponibilidad} from "src/app/disponibilidad/disponibilidad";
import { Paseador } from "src/app/paseador/paseador";


describe('DisponibilidadListarComponent', () => {
  let component: DisponibilidadListarComponent;
  let fixture: ComponentFixture<DisponibilidadListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DisponibilidadListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DisponibilidadListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    let paseador =
       new Paseador(
        faker.random.number(),
        faker.random.number(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        null,
        null,
        null,
      );
      component.disponibilidades =[
        new Disponibilidad(
          faker.random.number(),
          faker.date.future(2),
        faker.random.number(),
        paseador
        )];

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
