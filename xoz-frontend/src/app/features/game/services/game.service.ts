import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Move } from 'src/app/models/game';

@Injectable()
export class GameService {
  private apiUrl: string = 'http://localhost:8080/move'
    constructor(private http: HttpClient) { }
  
    addMove(move: Move) {
      return this.http.post(this.apiUrl, move)
    }
  
}
