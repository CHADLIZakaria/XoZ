import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { GameRemoteService } from './services/game-remote.service';
import { WebSocketService } from './services/web-socket.service';
import { GameStart } from 'src/app/models/game';
import { AsyncPipe, JsonPipe, NgIf } from '@angular/common';
import { Observable, of, tap } from 'rxjs';

@Component({
  selector: 'app-game-remote',
  standalone: true,
  imports: [HttpClientModule, JsonPipe, AsyncPipe],
  providers: [],
  templateUrl: './game-remote.component.html',
  styleUrl: './game-remote.component.less'
})
export class GameRemoteComponent implements OnInit {
  gameStart$: Observable<GameStart | null> = of(null);

  constructor(private webSocketService: WebSocketService) {
  }
  
  ngOnInit(): void {
    console.log("🎮 GameRemoteComponent initialized!");
    this.gameStart$ = this.webSocketService.getPartyUpdates().pipe(
      tap(data => console.log("🎮 Game Start Event Received:", data))
    );
  
    // 🔥 Force WebSocket activation
    this.webSocketService.getPartyUpdates().subscribe(data => {
      console.log("web "+data)
    });
    
  }
}
