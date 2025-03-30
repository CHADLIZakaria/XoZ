import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Party } from 'src/app/models/game';

@Injectable()
export class HomeService {
  private apiUrl: string = 'http://localhost:8080/'
  constructor(private http: HttpClient) { }

  saveLocalParty(): Observable<Party> {
    return this.http.post<Party>(`${this.apiUrl}party`, {})
  }

  saveRemoteParty(): Observable<Party> {
    return this.http.post<Party>(`${this.apiUrl}remote-party`, {})
  }
}
