import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import{HomeComponent} from './home/home.component';
import { ConocenosComponent } from './conocenos/conocenos.component';

const routes: Routes = [
  {path:'',redirectTo:'home', pathMatch:'full'},
  { path: 'home', component: HomeComponent},
  { path: 'conocenos', component: ConocenosComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRouting {
}
