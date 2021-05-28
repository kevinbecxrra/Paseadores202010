/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { ReservaListarComponent } from './reserva-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Factura } from './../../factura/factura';
import { Reserva } from './../reserva';

describe('ReservaListarComponent', () => {
  let component: ReservaListarComponent;
  let fixture: ComponentFixture<ReservaListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReservaListarComponent ],
      imports:[HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReservaListarComponent);
    component = fixture.componentInstance;
    let factura =  new Factura(
      1,
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.random.number(),
      faker.random.number(),
      faker.random.number(),
      null,
    );
    component.reservas = [
      new Reserva(
        1,
        faker.lorem.sentence(),
        faker.date.past(),
        faker.random.boolean(),
        faker.lorem.sentence(),
        faker.random.number(),
        factura,
        null,
        null,
        null,
        null
      )
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it("Should have an figcaption element ", () => {
    expect(debug.query(By.css("figcaption")).nativeElement.innerText).toContain(
      component.reservas[0].observaciones
    );

    expect(debug.query(By.css("figcaption")).nativeElement.innerText).toContain(
      component.reservas[0].factura.referencia
    );
  });
});
