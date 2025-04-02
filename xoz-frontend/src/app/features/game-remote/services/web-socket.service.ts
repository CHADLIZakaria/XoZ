import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { Subject } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { GameStart } from 'src/app/models/game';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private client!: Client;
  private partyUpdates = new Subject<GameStart>();

  constructor() {
    this.initWebsocket()
  }
  initWebsocket() {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      debug: (str) => console.log('STOMP Debug:', str)
    });
    this.client.onConnect = () => {
      console.log("✅ WebSocket Connected!");
      this.client.subscribe('/game-start-topic', (message) => {
        console.log("📩 WebSocket Message Received:", message); // Full message log
        const parsedMessage = JSON.parse(message.body);
        console.log("📩 Parsed Message:", parsedMessage); // Log parsed data
        this.partyUpdates.next(parsedMessage); // Emit parsed message to subscribers
      });
      console.log("🟢 Subscribed to /topic/game-start-topic");
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

