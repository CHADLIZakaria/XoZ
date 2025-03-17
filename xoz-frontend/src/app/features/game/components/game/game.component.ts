import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Game, Move } from 'src/app/models/game';
import { PopupComponent } from 'src/app/shared/popup/popup.component';
import { ButtonComponent } from "../../../../shared/button/button.component";
import { GameService } from '../../services/game.service';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [HttpClientModule, ButtonComponent, PopupComponent],
  providers: [GameService],
  templateUrl: './game.component.html',
  styleUrl: './game.component.less'
})
export class GameComponent implements OnInit {
  uidParty!: string;
  currentGame!: Game;
  gridArray: string[] = []
  moves: {[key: string]: number} = {}
  currentPlayer="p1";
  movesWin: Move[] = [];
  showModal = signal(false);

  constructor(private gameService: GameService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.uidParty = params.get('uid')!
      this.gameService.getParty(this.uidParty).subscribe(
        (data) => {
          this.currentGame = data.currentGame.game
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

  isMoveWin(position: string) {
    return this.movesWin.map(moveWin => moveWin.position).includes(position);
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
      id_player: this.currentPlayer=="p1" ? 1 : 2    
    }
    this.gameService.addMove(move).subscribe(data => {
      if(this.currentPlayer==="p1") {
        this.moves[position]=1
        this.currentPlayer = "p2"
      }
      else {
        this.moves[position]=2
        this.currentPlayer = "p1"
      }
      if(data.finished) {
        this.movesWin = data.movesWin;
        this.toggleModal()
      }
    });   
  }

  toggleModal() {
    this.showModal.update((visible) => !visible);
  }

  restartGame() {
    console.log("restart game")
    this.gameService.restartGame(this.uidParty).subscribe(
      data => {
        this.currentGame = data.currentGame.game
        this.moves = {}
        this.movesWin = []
      }
    )
  }

}
