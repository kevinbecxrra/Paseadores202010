/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { DisponibilidadService } from './disponibilidad.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

import faker from "faker";
import { Disponibilidad } from "./disponibilidad";
import { environment } from "../../environments/environment";

describe('Service: Disponibilidad', () => {
 let injector: TestBed;
 let service: DisponibilidadService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "paseadores/100/disponibilidades";
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DisponibilidadService]
    });
    injector = getTestBed();
    service = injector.get(DisponibilidadService);
    httpMock = injector.get(HttpTestingController);
  });
  afterEach(() => {
    httpMock.verify();
  });
  it("getPost() should return 10 records", () => {
    let mockPosts: Disponibilidad[] = [];

    for (let i = 1; i < 11; i++) {
      let disponibilidad = new Disponibilidad(
        i,
        faker.date.future(2),
        faker.random.number(),
        null
      );

      mockPosts.push(disponibilidad);
    }

    service.getDisponibilidades().subscribe((disponibilidades) => {
      expect(disponibilidades.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });

});
