import { Component } from '@angular/core';
import { ButtonComponent } from "../../../../shared/button/button.component";
import { Route, Router } from '@angular/router';
import { HomeService } from '../../services/home.service'
import { HttpClientModule } from '@angular/common/http';
import { WebSocketService } from 'src/app/features/game-remote/services/web-socket.service';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [ButtonComponent, HttpClientModule],
    providers: [HomeService, WebSocketService],
    templateUrl: './home.component.html',
    styleUrl: './home.component.less'
})
export class HomeComponent {

  constructor(private router: Router, private homeService: HomeService) {
  }

  goToParty() {
    this.homeService.saveLocalParty().subscribe(
      (data) => { 
        this.router.navigate([`/party/${data.uid}`])
      }
    );
  }

  goToPartyRemotly() {
   
    this.homeService.saveRemoteParty().subscribe({
      next: (data) => {
        console.log('Remote party saved, navigating...');
        this.router.navigate([`/remote-party/${data.uid}`]);
      },
      error: (err) => {
        console.error('Error saving remote party:', err);
      }
    });
    
    
  }
  

}
