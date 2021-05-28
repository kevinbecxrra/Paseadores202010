import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { By } from "@angular/platform-browser";
import { DebugElement } from "@angular/core";

import { TarjetaListarComponent } from "./tarjeta-listar.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import faker from "faker";
import { Tarjeta } from "../tarjeta";
import { Usuario } from "src/app/usuario/usuario";

describe("TarjetaListarComponent", () => {
  let component: TarjetaListarComponent;
  let fixture: ComponentFixture<TarjetaListarComponent>;
  let debug: DebugElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TarjetaListarComponent],
      imports: [HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TarjetaListarComponent);
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
    component.tarjetas = [
      new Tarjeta(
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.lorem.sentence(),
        usuario
      ),
    ];
    fixture.detectChanges();
    debug = fixture.debugElement;
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });




});
