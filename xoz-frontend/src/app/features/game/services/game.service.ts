import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GameResult, Move, Party } from 'src/app/models/game';

@Injectable()
export class GameService {
  private apiUrl: string = 'http://localhost:8080/'
    constructor(private http: HttpClient) { }
  
    addMove(move: Move): Observable<GameResult> {
      return this.http.post<GameResult>(`${this.apiUrl}move`, move)
    }  

    getParty(uid: string): Observable<Party> {
      return this.http.get<Party>(`${this.apiUrl}party/${uid}`)
    }

}
