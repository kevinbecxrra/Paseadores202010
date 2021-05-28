/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { PaseadorListarComponent } from './paseador-listar.component';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Rating } from "src/app/rating/rating";
import { Paseo } from "src/app/paseo/paseo";
import { Disponibilidad} from "src/app/disponibilidad/disponibilidad";
import { Paseador } from "src/app/paseador/paseador";
describe('PaseadorListarComponent', () => {
  let component: PaseadorListarComponent;
  let fixture: ComponentFixture<PaseadorListarComponent>;
  let debug: DebugElement;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaseadorListarComponent ],
      imports: [HttpClientTestingModule],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaseadorListarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    let rating = [
      new Rating(
        faker.random.number(),
        faker.random.number(),
        faker.lorem.sentence(),
        null,
        null
    )];
    let paseo = [
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
        null,
        null,
        null
      ),
    ];
      let disponibilidad =[
        new Disponibilidad(
          faker.random.number(),
          faker.date.future(2),
        faker.random.number(),
        null
        )];

    component.paseadores = [
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
      )];

    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it("Component has a table", () => {
    expect(debug.query(By.css("tbody")).childNodes.length).toBeGreaterThan(0);
  });
});
