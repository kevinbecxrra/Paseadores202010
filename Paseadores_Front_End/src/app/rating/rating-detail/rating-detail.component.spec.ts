/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement } from '@angular/core';
import faker from "faker";
import { Rating } from "src/app/rating/rating";

import { RatingDetailComponent } from './rating-detail.component';
import { Paseo } from 'src/app/paseo/paseo';
import { Paseador } from 'src/app/paseador/paseador';

describe('PaseaoDetailComponent', () => {
  let component: RatingDetailComponent;
  let fixture: ComponentFixture<RatingDetailComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RatingDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingDetailComponent);
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
    let paseo = new Paseo(
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
    );
    component.ratingDetail =
      new Rating(
        faker.random.number(),
       faker.random.number(),
       faker.lorem.sentence(),
       paseo,
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


