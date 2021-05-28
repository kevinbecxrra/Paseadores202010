/* tslint:disable:no-unused-variable */
import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { TarjetaService } from "./tarjeta.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";


import faker from "faker";
import { Tarjeta } from "./tarjeta";
import { environment } from "../../environments/environment";

describe("Service: Tarjeta", () => {
 let injector: TestBed;
 let service: TarjetaService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "usuarios/100/tarjetas";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [TarjetaService],
   });
   injector = getTestBed();
   service = injector.get(TarjetaService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });


});
