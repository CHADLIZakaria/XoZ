import { Routes } from '@angular/router';
import { GameComponent } from './features/game/components/game/game.component';
import { HomeComponent } from './features/home/components/home/home.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'party/:uid', component: GameComponent }
];