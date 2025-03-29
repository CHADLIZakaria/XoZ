import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Game, Move, Player } from 'src/app/models/game';
import { PopupComponent } from 'src/app/shared/popup/popup.component';
import { ButtonComponent } from "../../../../shared/button/button.component";
import { GameService } from '../../services/game.service';
import { animate, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [HttpClientModule, ButtonComponent, PopupComponent],
  providers: [GameService],
  templateUrl: './game.component.html',
  styleUrl: './game.component.less',
  animations: [
    trigger('btn-game', [
      transition('void => *', [
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
    trigger('fadeIn', [
      transition(':enter', [
        style({ filter: 'blur(3px)'}), 
        animate('500ms ease-in', style({ opacity: 1, filter: 'blur(0px)' }))
      ]),
      transition(':leave', [
        animate('2s ease-out', style({ opacity: 0.5, filter: 'blur(3px)' })) 
      ])
    ])
  ]
})
export class GameComponent implements OnInit {
  uidParty!: string;
  players!: Player[];
  currentGame!: Game;
  history!: Game[];
  gridArray: string[] = []
  moves: {[key: string]: number} = {}
  movesWin: Move[] = [];
  showModal = signal(false);
  showAnimation: 'player1' | 'player2' | 'draw' | 'hidden' = 'hidden';
  isLoading = false;

  constructor(
    private gameService: GameService, 
    private route: ActivatedRoute,
    private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.uidParty = params.get('uid')!
      this.isLoading = true;
      this.gameService.getParty(this.uidParty).subscribe(
        (data) => {
          this.isLoading = false;
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
    })
    this.gridArray = this.getData()
  }

  isMoveWin(position: string): boolean {
    return this.movesWin.map(moveWin => moveWin.position).includes(position);
  }

  getNumberWinPlayer(idPlayer: number): number {
    return this.history.filter(game => game.idWinner===idPlayer).length
  }

  getNumberDrawPlayer(): number {
    return this.history.filter(game => game.idWinner===null).length
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

  addMove(position: string) {
    if(this.moves.hasOwnProperty(position)) return;
    let move: Move = {
      id: undefined,
      position: position,
      id_game: this.currentGame.id,
      id_player: this.currentGame.idCurrentPlayer,
      id_next_player: this.currentGame.idCurrentPlayer === this.players[0].id ? this.players[1].id : this.players[0].id
    }
    this.gameService.addMove(move).subscribe(data => {
      this.moves[position] = this.currentGame.idCurrentPlayer
      if(this.currentGame.idCurrentPlayer===this.players[0].id) {
        this.currentGame.idCurrentPlayer = this.players[1].id
      }
      else {
        this.currentGame.idCurrentPlayer = this.players[0].id
      }
      if(data.finished) {
        this.currentGame = {
          ...this.currentGame, 
          finished: true,
          idWinner: data.movesWin.length > 0 ? data.movesWin[0].id_player : null
        }
        this.history.push(this.currentGame)
        if(data.movesWin.length > 0) {
          this.movesWin = data.movesWin;
          this.showAnimation = data.movesWin[0].id_player==this.players[0].id ? 'player1' : 'player2';
        }
        else {
          this.showAnimation = 'draw'
        }
        this.toggleModal()
      }
    });   
  }

  toggleModal() {
    this.showModal.update((visible) => !visible);
  }

  restartGame() {
    this.gameService.restartGame(this.uidParty).subscribe(
      data => {
        this.currentGame = data.currentGame.game
        this.moves = {}
        this.movesWin = []
      }
    )
  }

  resetGame() {
    this.gameService.resetGame(this.uidParty).subscribe(
      data => {
        this.currentGame = data.currentGame.game
        this.moves = {}
        this.movesWin = []
      }
    )
  }

  exit() {
    this.gameService.deleteParty(this.uidParty).subscribe(() => {
      this.router.navigate(['/'])
    })
  }

}
