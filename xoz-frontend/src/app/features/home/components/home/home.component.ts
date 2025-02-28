import { Component } from '@angular/core';
import { ButtonComponent } from "../../../../shared/button/button.component";
import { Route, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [ButtonComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.less'
})
export class HomeComponent {

  constructor(private router: Router) {

  }

  handleClick() {
    console.log("change routing")
    this.router.navigate(['/game'])
  }

}
