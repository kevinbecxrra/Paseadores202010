/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import faker from "faker";
import { Paseo } from "src/app/paseo/paseo";

import { PaseoDetailComponent } from './paseo-detail.component';
import { Paseador } from 'src/app/paseador/paseador';

describe('PaseaoDetailComponent', () => {
  let component: PaseoDetailComponent;
  let fixture: ComponentFixture<PaseoDetailComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaseoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaseoDetailComponent);
    component = fixture.componentInstance;
    let paseador = new Paseador(
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
          null

    );
    component.paseoDetail =
      new Paseo(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        faker.random.boolean(),
        faker.random.boolean(),
        faker.random.number(),
        null,
        paseador,
        null,
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
