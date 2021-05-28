import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { UsuarioListarComponent } from "./usuario-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Tarjeta } from "src/app/tarjeta/tarjeta";
import { Usuario } from "src/app/usuario/usuario";
import { Mascota } from 'src/app/mascota/mascota';

describe("UsuarioListarComponent", () => {
  let component: UsuarioListarComponent;
  let fixture: ComponentFixture<UsuarioListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UsuarioListarComponent],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsuarioListarComponent);
    component = fixture.componentInstance;
    let mascotas = [
      new Mascota(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.boolean(),
        null,
        null
    )];
    let tarjetas = [
      new Tarjeta(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.lorem.sentence(),
        null
      ),
    ];
    component.usuarios = [
      new Usuario(
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
