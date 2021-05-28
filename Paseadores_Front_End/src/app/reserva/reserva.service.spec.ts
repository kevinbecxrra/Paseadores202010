/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { ReservaService } from './reserva.service';
import { FacturaService } from './../factura/factura.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

 import faker from "faker";
 import { environment } from '../../environments/environment';
 import { Reserva } from './reserva';

 describe('Service: Reserva', () => {
 let injector: TestBed;
 let service: ReservaService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "reservas";
 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [ReservaService]
   });
   injector = getTestBed();
   service = injector.get(ReservaService);
   httpMock = injector.get(HttpTestingController)
 });
 afterEach(() => {
   httpMock.verify();
 });
 it("getPost() should return 10 records", () => {
  let mockPosts: Reserva[] = [];

  for (let i = 1; i < 11; i++) {
    let reserva = new Reserva(
     i,
     faker.lorem.sentence(),
     faker.date.past(),
     faker.random.boolean(),
     faker.lorem.sentence(),
     faker.random.number(),
     null,
     null,
     null,
     null,
     null
    );

    mockPosts.push(reserva);
  }

  service.getReservas().subscribe((reservas) => {
    expect(reservas.length).toBe(10);
  });

  const req = httpMock.expectOne(apiUrl);
  expect(req.request.method).toBe("GET");
  req.flush(mockPosts);
  });
});
