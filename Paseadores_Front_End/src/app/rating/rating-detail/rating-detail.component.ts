import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Rating } from '../rating';

@Component({
  selector: 'app-rating-detail',
  templateUrl: './rating-detail.component.html',
  styleUrls: ['./rating-detail.component.css']
})
export class RatingDetailComponent implements OnInit {

  @Input() ratingDetail: Rating;

  constructor() { }

  ngOnInit() {

  }
}
