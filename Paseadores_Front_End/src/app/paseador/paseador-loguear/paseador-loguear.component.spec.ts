/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PaseadorLoguearComponent } from './paseador-loguear.component';

describe('PaseadorLoguearComponent', () => {
  let component: PaseadorLoguearComponent;
  let fixture: ComponentFixture<PaseadorLoguearComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaseadorLoguearComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaseadorLoguearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
