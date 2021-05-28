/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from '@angular/core/testing';
import { FacturaService } from './factura.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";
import faker from "faker";
import { Factura } from './factura';
import { environment } from '../../environments/environment';

describe('Service: Factura', () => {
  let injector: TestBed;
  let service: FacturaService;
  let httpMock: HttpTestingController;
  let apiUrl = environment.baseUrl + "facturas";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpTestingController],
      providers: [FacturaService]
    });
    injector = getTestBed();
    service = injector.get(FacturaService);
    httpMock =  injector.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it("getFacturas() should return 10 records", () => {
    let mockPosts: Factura[] = [];
    for (let i = 1; i < 11; i++) {
      let factura = new Factura(
        i,
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        null
      );
      mockPosts.push(factura);
    }

    service.getFacturas().subscribe((facturas) => {
      expect(facturas.length).toBe(10);
    });

    const req = httpMock.expectOne(apiUrl);
    expect(req.request.method).toBe("GET");
    req.flush(mockPosts);
  });
});

