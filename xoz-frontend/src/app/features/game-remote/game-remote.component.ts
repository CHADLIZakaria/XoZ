import { Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { GameRemoteService } from './services/game-remote.service';
import { WebSocketService } from './services/web-socket.service';

@Component({
  selector: 'app-game-remote',
  standalone: true,
  imports: [HttpClientModule],
  providers: [GameRemoteService, WebSocketService],
  templateUrl: './game-remote.component.html',
  styleUrl: './game-remote.component.less'
})
export class GameRemoteComponent implements OnInit {
  messages: string[] = [];

  constructor(private webSocketService: WebSocketService, private gameRemoteService: GameRemoteService) {}

  ngOnInit(): void {
    this.gameRemoteService.save().subscribe((data) => {
      console.log(data)
    })
    this.webSocketService.getPartyUpdates().subscribe((message) => {
      this.messages.push(message);
      console.log(message)
    });
  }


}
