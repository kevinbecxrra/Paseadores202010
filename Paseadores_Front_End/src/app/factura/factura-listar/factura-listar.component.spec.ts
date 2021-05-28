/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { FacturaListarComponent } from './factura-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Factura } from './../factura';
import { Tarjeta } from 'src/app/tarjeta/tarjeta';

describe('FacturaListarComponent', () => {
  let component: FacturaListarComponent;
  let fixture: ComponentFixture<FacturaListarComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacturaListarComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacturaListarComponent);
    component = fixture.componentInstance;
    let tarjeta : Tarjeta = new Tarjeta(
      1,
      faker.lorem.sentence(),
      faker.random.number(),
      faker.random.number(),
      faker.lorem.sentence(),
      null
    );
    component.facturas = [
      new Factura(
        1,
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        tarjeta
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
      component.facturas[0].referencia
    );

    expect(debug.query(By.css("figcaption")).nativeElement.innerText).toContain(
      component.facturas[0].tarjeta.tipo
    );
  });
});
