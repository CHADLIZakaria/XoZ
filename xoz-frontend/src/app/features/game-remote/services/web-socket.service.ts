import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { GameStart } from 'src/app/models/game';

@Injectable()
export class WebSocketService {
private client!: Client;
  private partyUpdates = new Subject<GameStart>();

  constructor() {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      debug: (str) => console.log('STOMP Debug:', str)
    });

    this.client.onConnect = () => {
      console.log("✅ WebSocket Connected!");
      this.client.subscribe('/party-topic', (message) => {
        console.log("📩 Received message:", message.body);
        this.partyUpdates.next(JSON.parse(message.body));
      });
    };
    this.client.onStompError = (frame) => {
      console.error("❌ STOMP error:", frame);
    };

    this.client.activate();
  }

  getPartyUpdates() {
    return this.partyUpdates.asObservable();
  }

}
