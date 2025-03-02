import { RouterOutlet } from '@angular/router';
import { Component } from '@angular/core';
import { NavbarComponent } from './shared/navbar/navbar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
  imports: [RouterOutlet, NavbarComponent]
})
export class AppComponent {
  title = 'xo-frontend';

}
