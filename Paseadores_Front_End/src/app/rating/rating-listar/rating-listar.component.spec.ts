import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { RatingListarComponent } from "./rating-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Rating } from "../rating";
import { Paseo } from "src/app/paseo/paseo";
import { Paseador } from 'src/app/paseador/paseador';

describe("RatingListarComponent", () => {
  let component: RatingListarComponent;
  let fixture: ComponentFixture<RatingListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [RatingListarComponent],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RatingListarComponent);
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
    component.ratings = [
      new Rating(
       faker.random.number(),
       faker.random.number(),
       faker.lorem.sentence(),
       paseo,
       null

      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });

  it("Component has a table", () => {
    expect(debug.query(By.css("tbody")).childNodes.length).toBeGreaterThan(0);
  });
});
