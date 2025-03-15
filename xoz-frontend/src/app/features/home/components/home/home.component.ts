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
        this.router.navigate([`/party/${data.uid}`])
      }
    );
  }

}
