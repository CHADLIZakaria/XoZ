import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GameResult, GameStart, Move, Party } from 'src/app/models/game';

@Injectable()
export class GameRemoteService {
  private apiUrl: string = 'http://localhost:8080/api/remote-party'

  constructor(private http: HttpClient) { }

  save(): Observable<Party> {
    return this.http.post<Party>(`${this.apiUrl}`, {})
  }

  get(uid: string): Observable<GameStart> {
    return this.http.get<GameStart>(`${this.apiUrl}/default/${uid}`)
  }

  findParty(uid: string): Observable<Party> {
    return this.http.get<Party>(`${this.apiUrl}/${uid}`)
  }

  addMove(move: Move): Observable<GameResult> {
    return this.http.post<GameResult>(`${this.apiUrl}/move`, move)
  }

  restartGame(uid: string) {
    return this.http.get<Party>(`${this.apiUrl}${uid}/restart`)
  }



}
