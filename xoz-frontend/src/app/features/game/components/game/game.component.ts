import { Component, OnInit } from '@angular/core';
import { GameService } from '../../services/game.service';
import { Move } from 'src/app/models/game';
import { HttpClientModule } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [HttpClientModule],
  providers: [GameService],
  templateUrl: './game.component.html',
  styleUrl: './game.component.less'
})
export class GameComponent implements OnInit {
  idGame!:number;
  gridArray: string[] = []
  moves: {[key: string]: string} = {}
  currentPlayer="p1";

  constructor(private gameService: GameService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(param => {
      this.idGame = param['id']    
      this.gridArray = this.getData()
    })
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
      id_game: +this.idGame!,
      id_player: this.currentPlayer=="p1" ? 1 : 2    
    }
    console.log(move)
    this.gameService.addMove(move).subscribe();
    if(this.currentPlayer==="p1") {
      this.moves[position]="x"
      this.currentPlayer = "p2"
    }
    else {
      this.moves[position]="o"
      this.currentPlayer = "p1"
    }
    console.log(this.moves)
  }

}
