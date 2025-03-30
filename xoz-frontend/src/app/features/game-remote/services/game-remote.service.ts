import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Party } from 'src/app/models/game';

@Injectable()
export class GameRemoteService {
  private apiUrl: string = 'http://localhost:8080/'

  constructor(private httpClient: HttpClient) { }

  save() {
    return this.httpClient.post<Party>(`${this.apiUrl}remote-party`, {})
  }
}
