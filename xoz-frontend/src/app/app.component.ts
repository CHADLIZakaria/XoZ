import { RouterOutlet } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { JsonPipe } from '@angular/common';
import { HomeComponent } from "./features/home/components/home/home.component";
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