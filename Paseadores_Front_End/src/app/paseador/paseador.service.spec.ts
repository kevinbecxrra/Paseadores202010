/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PaseadorService } from './paseador.service';

import {
  HttpTestingController,
  HttpClientTestingModule,
 } from "@angular/common/http/testing";

 import faker from "faker";
import { Paseador } from "./paseador";
import { environment } from "../../environments/environment";

describe('Service: Paseador', () => {
  beforeEach(() => {
    let injector: TestBed;
    let service: PaseadorService;
    let httpMock: HttpTestingController;
    let apiUrl = environment.baseUrl + "paseadores";

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PaseadorService]
    });

    afterEach(() => {
      httpMock.verify();
    });

    it("getPost() should return 10 records", () => {
      let mockPosts: Paseador[] = [];

      for (let i = 1; i < 11; i++) {
        let paseador = new Paseador(
          i,
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

        mockPosts.push(paseador);
      }

      service.getPaseadores().subscribe((paseador) => {
        expect(paseador.length).toBe(10);
      });

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe("GET");
      req.flush(mockPosts);
    });
  });

});
