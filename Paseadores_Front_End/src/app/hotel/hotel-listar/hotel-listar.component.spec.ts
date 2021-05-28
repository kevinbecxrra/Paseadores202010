/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { HotelListarComponent } from './hotel-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Hotel } from "../hotel";

describe('HotelListarComponent', () => {
  let component: HotelListarComponent;
  let fixture: ComponentFixture<HotelListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HotelListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HotelListarComponent);
    component = fixture.componentInstance;
    let hotel = new Hotel(
      faker.random.number(),
      faker.random.number(),
      faker.random.number(),
      faker.random.boolean(),
      null
    );
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
