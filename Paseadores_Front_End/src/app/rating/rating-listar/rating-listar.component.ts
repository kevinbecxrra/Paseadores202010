import { Component, OnInit } from '@angular/core';
import { Rating } from '../rating';
import { RatingService } from '../rating.service'

@Component({
  selector: 'app-rating-listar',
  templateUrl: './rating-listar.component.html',
  styleUrls: ['./rating-listar.component.css']
})
export class RatingListarComponent implements OnInit {

  ratings: Array<Rating>;
  ratingSeleccionado: Rating;
  selected=false;

  constructor(private ratingService: RatingService) { }

  onSelected(m: Rating){
    this.ratingSeleccionado = m;
    this.selected = true;
  }

  ngOnInit() {
    this.getRatings();
  }

  getRatings(): void{
   this.ratingService.getRatings().subscribe(ratings=>{
     this.ratings = ratings;
   })
  }

}
