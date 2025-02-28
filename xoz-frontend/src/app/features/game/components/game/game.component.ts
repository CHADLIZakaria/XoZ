import { Component } from '@angular/core';

@Component({
  selector: 'app-game',
  standalone: true,
  imports: [],
  templateUrl: './game.component.html',
  styleUrl: './game.component.less'
})
export class GameComponent {
  gridArray: string[] = []
  moves: {[key: string]: string} = {}
  currentPlayer="p1";

  ngOnInit(): void {
    this.gridArray = this.getData()
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
    if(this.currentPlayer==="p1") {
      this.moves[position]="X"
      this.currentPlayer = "p2"
    }
    else {
      this.moves[position]="O"
      this.currentPlayer = "p1"
    }
    console.log(this.moves)
  }

}
