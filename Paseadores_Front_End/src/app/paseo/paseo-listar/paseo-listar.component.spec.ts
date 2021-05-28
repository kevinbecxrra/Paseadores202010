/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { PaseoListarComponent } from "./paseo-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Paseador } from "src/app/paseador/paseador";
import { Paseo } from "src/app/paseo/paseo";


describe('PaseoListarComponent', () => {
  let component: PaseoListarComponent;
  let fixture: ComponentFixture<PaseoListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PaseoListarComponent],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaseoListarComponent);
    component = fixture.componentInstance;
    let paseador = [
      new Paseador(
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
        null
    )];
    component.paseos = [
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
