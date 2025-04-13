import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-waiting-player',
  imports: [],
  templateUrl: './waiting-player.component.html',
  styleUrl: './waiting-player.component.less',
  animations: [
    trigger('repeatAnimation', [
      state('open', style({ transform: 'rotate(320deg)' })),
      state('closed', style({ transform: 'rotate(0deg)' })),
      transition('open => closed', animate('1000ms ease-out')),
      transition('closed => open', animate('1000ms ease-in'))
    ])
  ]
})
export class WaitingPlayerComponent implements OnInit {
  images = [
    'x',
    'console',
    'console',
    'o'
  ];
  animationStates: ('open' | 'closed')[] = [];

  ngOnInit(): void {
    this.animationStates = this.images.map(() => 'closed');
    let current = 0;
    setInterval(() => {
      this.animationStates = this.images.map(() => 'closed');
      this.animationStates[current] = 'open';
      current = (current + 1) % this.images.length;
    }, 1000);
  }
}
