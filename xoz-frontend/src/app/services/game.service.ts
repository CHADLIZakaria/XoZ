import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Game } from '../models/game';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  private apiUrl: string = 'http://localhost:8080/game'

  constructor(private http: HttpClient) { }

  save() {
    const game = {};
    this.http.post(this.apiUrl, game).subscribe(data => {
      console.log(data)
    })
  }

}
