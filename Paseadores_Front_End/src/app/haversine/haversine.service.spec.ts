/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { HaversineService } from "./haversine.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { Haversine } from "./haversine";
import { environment } from "../../environments/environment";

describe("Service: Haversine", () => {
 let injector: TestBed;
 let service: HaversineService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "haversines";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [HaversineService],
   });
   injector = getTestBed();
   service = injector.get(HaversineService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 10 records", () => {
   let mockPosts: Haversine[] = [];

   for (let i = 1; i < 11; i++) {
     let haversine = new Haversine(
       i,
       faker.random.number(),
       faker.random.number(),
       faker.random.number(),
       null,
     );

     mockPosts.push(haversine);
   }

   service.getHaversines().subscribe((haversines) => {
     expect(haversines.length).toBe(10);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
