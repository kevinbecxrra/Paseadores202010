import { Input } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { Paseo } from '../paseo';
import { ActivatedRoute, Router } from '@angular/router';
import { PaseoService } from '../paseo.service';

@Component({
  selector: 'app-paseo-detail',
  templateUrl: './paseo-detail.component.html',
  styleUrls: ['./paseo-detail.component.css']
})
export class PaseoDetailComponent implements OnInit {
  paseoId:Number;
  @Input() paseoDetail: Paseo;

  constructor(private route: ActivatedRoute,
  private router: Router, private paseoService:PaseoService) { }

  ngOnInit() {
    if (this.paseoDetail === undefined) {
      console.log('routerLink');
      this.paseoId = +this.route.snapshot.paramMap.get('id');
      this.getPaseoDetail();
    } else { console.log(this.paseoDetail.id); }
  }

  getPaseoDetail(): void {
    this.paseoService.getPaseoDetail(this.paseoId)
      .subscribe(paseoDetail => {
        this.paseoDetail = paseoDetail;
      });
    }
}
