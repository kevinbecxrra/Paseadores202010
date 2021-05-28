/* tslint:disable:no-unused-variable */
import { TestBed, async, inject, getTestBed } from "@angular/core/testing";
import { RatingService } from "./rating.service";

import {
 HttpTestingController,
 HttpClientTestingModule,
} from "@angular/common/http/testing";

import faker from "faker";
import { Rating } from "./rating";
import { environment } from "../../environments/environment";

describe("Service: Rating", () => {
 let injector: TestBed;
 let service: RatingService;
 let httpMock: HttpTestingController;
 let apiUrl = environment.baseUrl + "ratings";

 beforeEach(() => {
   TestBed.configureTestingModule({
     imports: [HttpClientTestingModule],
     providers: [RatingService],
   });
   injector = getTestBed();
   service = injector.get(RatingService);
   httpMock = injector.get(HttpTestingController);
 });

 afterEach(() => {
   httpMock.verify();
 });

 it("getPost() should return 10 records", () => {
   let mockPosts: Rating[] = [];

   for (let i = 1; i < 11; i++) {
     let rating = new Rating(
       i,
       faker.random.number(),
       faker.lorem.sentence(),
       null,
       null
     );

     mockPosts.push(rating);
   }

   service.getRatings().subscribe((ratings) => {
     expect(ratings.length).toBe(10);
   });

   const req = httpMock.expectOne(apiUrl);
   expect(req.request.method).toBe("GET");
   req.flush(mockPosts);
 });
});
