/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import faker from "faker";
import { Mascota } from "src/app/mascota/mascota";

import { MascotaDetalleComponent } from './mascota-detalle.component';
import { Usuario } from 'src/app/usuario/usuario';

describe('MascotaDetalleComponent', () => {
  let component: MascotaDetalleComponent;
  let fixture: ComponentFixture<MascotaDetalleComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MascotaDetalleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MascotaDetalleComponent);
    component = fixture.componentInstance;
    let usuario = new Usuario(
      faker.random.number(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      null,
      null
    );
    component.mascotaDetail =
      new Mascota(
       faker.random.number(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.random.number(),
       faker.random.boolean(),
       usuario,
       null
      )
    ;
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
