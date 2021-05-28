/* tslint:disable:no-unused-variable */

import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { HotelService } from "./hotel.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { Hotel } from "./hotel";
import { environment } from "../../environments/environment";

describe("Service: Hotel", () => {
 let injector: TestBed;
 let service: HotelService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "hotels";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [HotelService],
   });
   injector = getTestBed();
   service = injector.get(HotelService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 1 records", () => {
   let mockPosts: Hotel[] = [];

   for (let i = 1; i < 2; i++) {
     let hotel = new Hotel(
       i,
       faker.random.number(),
       faker.random.number(),
       faker.random.boolean(),
       null,
     );

     mockPosts.push(hotel);
   }

   service.getHotels().subscribe((hotels) => {
     expect(hotels.length).toBe(1);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
