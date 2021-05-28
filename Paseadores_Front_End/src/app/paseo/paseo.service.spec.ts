/* tslint:disable:no-unused-variable */
import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { PaseoService } from "./paseo.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";


import faker from "faker";
import { Paseo } from "./paseo";
import { environment } from "../../environments/environment";

describe("Service: Paseo", () => {
 let injector: TestBed;
 let service: PaseoService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "paseos";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [PaseoService],
   });
   injector = getTestBed();
   service = injector.get(PaseoService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 10 records", () => {
   let mockPosts: Paseo[] = [];

   for (let i = 1; i < 11; i++) {
     let paseo = new Paseo(
       i,
        faker.lorem.sentence(),
        faker.random.number(),
        faker.random.number(),
        faker.random.number(),
        faker.random.boolean(),
        faker.random.boolean(),
        faker.random.number(),
        null,
        null,
        null,
        null
     );

     mockPosts.push(paseo);
   }

   service.getPaseos().subscribe((paseos) => {
     expect(paseos.length).toBe(10);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
