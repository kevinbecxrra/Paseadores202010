/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import faker from "faker";
import { Haversine } from "src/app/haversine/haversine";

import { HaversineDetailComponent } from './haversine-detail.component';

describe('HaversineDetailComponent', () => {
  let component: HaversineDetailComponent;
  let fixture: ComponentFixture<HaversineDetailComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HaversineDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HaversineDetailComponent);
    component = fixture.componentInstance;
    new Haversine(
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
