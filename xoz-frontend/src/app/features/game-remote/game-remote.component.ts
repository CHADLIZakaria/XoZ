import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { GameRemoteService } from './services/game-remote.service';
import { WebSocketService } from './services/web-socket.service';
import { GameStart } from 'src/app/models/game';
import { AsyncPipe, JsonPipe, NgIf } from '@angular/common';
import { Observable, of, Subscription, tap } from 'rxjs';

@Component({
    selector: 'app-game-remote',
    standalone: true,
    imports: [JsonPipe, AsyncPipe],
    providers: [WebSocketService],
    templateUrl: './game-remote.component.html',
    styleUrl: './game-remote.component.less'
})
export class GameRemoteComponent implements OnInit {
  gameStart$: Observable<GameStart | null> = of(null);
  private sub!: Subscription;

  constructor(private webSocketService: WebSocketService) {}

  ngOnInit(): void {
    this.webSocketService.connect();
    this.sub = this.webSocketService.onParty().subscribe({
      next: (party) => {
        console.log('Party received:', party);
        this.gameStart$ = of(party);
      },
      error: (err) => {
        console.error('Error receiving party data:', err); // Log any errors
      }
    });
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
