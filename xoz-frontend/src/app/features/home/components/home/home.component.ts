import { Component } from '@angular/core';
import { ButtonComponent } from "../../../../shared/button/button.component";
import { Route, Router } from '@angular/router';
import { HomeService } from '../../services/home.service'
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonComponent, HttpClientModule],
  providers: [HomeService], 
  templateUrl: './home.component.html',
  styleUrl: './home.component.less'
})
export class HomeComponent {

  constructor(private router: Router, private homeService: HomeService) {
  }

  handleClick() {
    this.homeService.save().subscribe(
      (data) => { 
        console.log(data.id.toString())       
        this.router.navigate(['/game'], {queryParams: {id: data.id.toString()}})
      }
    );
  }

}
