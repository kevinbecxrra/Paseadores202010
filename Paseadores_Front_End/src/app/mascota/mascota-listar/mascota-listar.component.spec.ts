import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { MascotaListarComponent } from "./mascota-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Mascota } from "../mascota";
import { Usuario } from "src/app/usuario/usuario";

describe("MascotaListarComponent", () => {
  let component: MascotaListarComponent;
  let fixture: ComponentFixture<MascotaListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MascotaListarComponent],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MascotaListarComponent);
    component = fixture.componentInstance;
    let usuario = new Usuario(
      faker.random.number(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      faker.random.number(),
      faker.lorem.sentence(),
      null,
      null
    );
    component.mascotas = [
      new Mascota(
       faker.random.number(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.lorem.sentence(),
       faker.random.number(),
       faker.random.boolean(),
       usuario,
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
