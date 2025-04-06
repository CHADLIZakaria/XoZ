import { ChangeDetectorRef, Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { GameRemoteService } from './services/game-remote.service';
import { WebSocketService } from './services/web-socket.service';
import { GameStart } from 'src/app/models/game';
import { AsyncPipe, CommonModule, JsonPipe } from '@angular/common';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { LoadingComponent } from "./components/loading/loading.component";
import { GameHeaderComponent } from "./components/game-header/game-header.component";
import { GameGridComponent } from "./components/game-grid/game-grid.component";

@Component({
  selector: 'app-game-remote',
  standalone: true,
  imports: [JsonPipe, AsyncPipe, CommonModule, HttpClientModule, LoadingComponent, GameHeaderComponent, GameGridComponent],
  providers: [WebSocketService, GameRemoteService],
  templateUrl: './game-remote.component.html',
  styleUrl: './game-remote.component.less'
})
export class GameRemoteComponent implements OnInit, OnDestroy {
  private sub!: Subscription;
  private connectionSub!: Subscription;
  gameStart$: GameStart | null = null;

  constructor(
    private webSocketService: WebSocketService,
    private activatedRoute: ActivatedRoute,
    private gameRemoteService: GameRemoteService
  ) {}

  ngOnInit(): void {
    this.webSocketService.initWebsocket();

    this.sub = this.webSocketService.onParty().subscribe({
      next: (party) => {
        console.log('Party received (WS):', party);
        this.gameStart$ = party;
      },
      error: (err) => {
        console.error('Error receiving party data:', err);
      }
    });

    this.connectionSub = this.webSocketService.getConnectionStatus().subscribe((connected) => {
      if (connected && !this.gameStart$) {
        const uid = this.activatedRoute.snapshot.paramMap.get('uid')!
        this.gameRemoteService.get(uid).subscribe({
          next: (party) => {
            console.log('Party fetched (HTTP):', party);
            this.gameStart$ = party;
          },
          error: (err) => {
            console.error('Error fetching current party:', err);
          }
        });
      }
    });
  }

  ngOnDestroy(): void {
    if (this.sub) {
      this.sub.unsubscribe();
    }
    if (this.connectionSub) {
      this.connectionSub.unsubscribe();
    }
    this.webSocketService.disconnect();
  }
}
