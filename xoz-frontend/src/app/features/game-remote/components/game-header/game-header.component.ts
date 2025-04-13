import { Component } from '@angular/core';

@Component({
  selector: 'app-game-header',
  imports: [],
  templateUrl: './game-header.component.html',
  styleUrl: './game-header.component.less'
})
export class GameHeaderComponent {
  showAnimation: 'player1' | 'player2' | 'draw' | 'hidden' = 'hidden';
}
