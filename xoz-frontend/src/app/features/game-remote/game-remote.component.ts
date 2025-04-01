import { Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { GameRemoteService } from './services/game-remote.service';
import { WebSocketService } from './services/web-socket.service';
import { GameStart } from 'src/app/models/game';
import { AsyncPipe, JsonPipe, NgIf } from '@angular/common';
import { Observable, tap } from 'rxjs';

@Component({
  selector: 'app-game-remote',
  standalone: true,
  imports: [HttpClientModule, JsonPipe, AsyncPipe],
  providers: [GameRemoteService, WebSocketService],
  templateUrl: './game-remote.component.html',
  styleUrl: './game-remote.component.less'
})
export class GameRemoteComponent implements OnInit {
  gameStart$: Observable<GameStart | null>;

  constructor(private webSocketService: WebSocketService) {
    this.gameStart$ = this.webSocketService.getPartyUpdates();
    this.gameStart$.pipe(tap(data => console.log(data)))
  }

  ngOnInit(): void {
    
  }
}
