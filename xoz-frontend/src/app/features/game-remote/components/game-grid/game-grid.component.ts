import { Component, input, OnInit, signal } from '@angular/core';
import { Game, Move, Player } from 'src/app/models/game';
import { GameRemoteService } from '../../services/game-remote.service';
import { WebSocketService } from '../../services/web-socket.service';
import { animate, style, transition, trigger } from '@angular/animations';
import { WaitingPlayerComponent } from "../waiting-player/waiting-player.component";
import { PopupComponent } from 'src/app/shared/popup/popup.component';

@Component({
  selector: 'app-game-grid',
  imports: [WaitingPlayerComponent, PopupComponent],
  templateUrl: './game-grid.component.html',
  styleUrl: './game-grid.component.less',
  animations: [
    trigger('btn-game', [
        transition('void <=> *', [
            style({
                opacity: 0,
                transform: 'scale(0.8)'
            }),
            animate('250ms', style({
                opacity: 1,
                transform: 'scale(1)'
            }))
        ])
    ]),
  ]
})
export class GameGridComponent implements OnInit  {
  gridArray: string[] = []
  moves: {[key: string]: number} = {}
  currentGame!: Game;
  players!: Player[];
  uidParty = input.required<string>()
  history: Game[] = []
  idPlayer = input<number>()
  movesWin: Move[] = [];
  showAnimation: 'player1' | 'player2' | 'draw' | 'hidden' = 'hidden';
  showModal = signal(false);
  

  constructor(
    private gameRemoteService: GameRemoteService, 
    private webSocketService: WebSocketService) {
  }
  
  
  ngOnInit(): void {
    this.gridArray =  this.getData()
    this.webSocketService.onMoves().subscribe({
      next: (moves) => {
        this.moves = moves.reduce((acc, element) => {
          acc[element.position] = element.id_player;
          return acc;
        }, {} as Record<string, number>);
        console.log('Moves received (WS):', moves);
        this.switchCurrentPlayer()
        
      },
      error: (err) => {
        console.error('Error receiving party data:', err);
      }
    });
    this.webSocketService.onGame().subscribe({
      next: (gameResult) => {
        if(gameResult.finished) {
          this.currentGame = {
            ...this.currentGame, 
            finished: true,
            idWinner: gameResult.movesWin.length > 0 ? gameResult.movesWin[0].id_player : null
          }
          this.history.push(this.currentGame)
          if(gameResult.movesWin.length > 0) {
            this.movesWin = gameResult.movesWin;
            this.showAnimation = gameResult.movesWin[0].id_player==this.players[0].id ? 'player1' : 'player2';
          }
          else {
            this.showAnimation = 'draw'
          }
          this.toggleModal()
        }
      }
    })
    this.gameRemoteService.findParty(this.uidParty()).subscribe(
      (data) => {
        console.log(data)
        this.currentGame = data.currentGame.game
        this.history = data.history.filter(game => game.finished===true)
        this.players = data.players.sort((player1, player2) => player1.id - player2.id)
        this.moves = data.currentGame.game.moves.reduce((acc, element) => {
          acc[element.position] = element.id_player;
          return acc;
        }, {} as Record<string, number>); 
        if(this.currentGame.finished) {
          this.toggleModal()
        }      
      }
    )
  }
  
  getData(): string[] {
    let data = []
    for(let i=0; i<3; i++) {
      for(let j=0;j<3; j++) {
        data.push(i+","+j)
      }
    }
    return data
  }

  isMoveWin(position: string): boolean {
    return this.movesWin.map(moveWin => moveWin.position).includes(position);
  }

  addMove(position: string) {
    if(this.moves.hasOwnProperty(position)) return;
    let move: Move = {
      id: undefined,
      position: position,
      id_game: this.currentGame.id,
      id_player: this.currentGame.idCurrentPlayer,
      id_next_player: this.currentGame.idCurrentPlayer === this.players[0].id ? this.players[1].id : this.players[0].id
    }
    this.gameRemoteService.addMove(move).subscribe(data => {
      this.moves[position] = this.currentGame.idCurrentPlayer
    });   
  }

  toggleModal() {
    this.showModal.update((visible) => !visible);
  }

  switchCurrentPlayer(): void {
    if(this.currentGame.idCurrentPlayer===this.players[0].id) {
      this.currentGame.idCurrentPlayer = this.players[1].id
    }
    else {
      this.currentGame.idCurrentPlayer = this.players[0].id
    }
  }

  restartGame() {
    this.gameRemoteService.restartGame(this.uidParty()).subscribe(
      data => {
        this.currentGame = data.currentGame.game
        this.moves = {}
        this.movesWin = []
      }
    )
  }

}
