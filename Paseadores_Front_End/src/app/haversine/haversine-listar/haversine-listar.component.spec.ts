/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { HaversineListarComponent } from './haversine-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Haversine } from "../haversine";

describe('HaversineListarComponent', () => {
  let component: HaversineListarComponent;
  let fixture: ComponentFixture<HaversineListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HaversineListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HaversineListarComponent);
    component = fixture.componentInstance;
    let haversine = new Haversine(
      faker.random.number(),
      faker.random.number(),
      faker.random.number(),
      faker.random.number(),
      null
    );
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
