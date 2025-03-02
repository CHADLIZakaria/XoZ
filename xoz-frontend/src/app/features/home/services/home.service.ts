import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Game } from 'src/app/models/game';

@Injectable()
export class HomeService {
  private apiUrl: string = 'http://localhost:8080/game'
  constructor(private http: HttpClient) { }

  save(): Observable<Game> {
    const game = {};
    return this.http.post<Game>(this.apiUrl, game)
  }
}
