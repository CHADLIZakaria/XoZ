import { Injectable } from '@angular/core';
import { Client } from '@stomp/stompjs';
import { BehaviorSubject, Observable } from 'rxjs';
import * as SockJS from 'sockjs-client';
import { GameStart } from 'src/app/models/game';

@Injectable()
export class WebSocketService {
  private stompClient!: Client;
  private partyUpdates = new BehaviorSubject<GameStart | null>(null); // Ensure it emits values

  constructor() {
    this.initWebsocket();
  }

  initWebsocket() {
    const socket = new SockJS("wss://localhost:8080/ws");

    this.stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => console.log('STOMP Debug:', str),
    });

    this.stompClient.onConnect = () => {
      console.log("✅ WebSocket Connected!");
      this.stompClient.subscribe('aparty-topic', (message) => {
        console.log("📩 WebSocket Message Received:", message.body);
        try {
          const parsedMessage: GameStart = JSON.parse(message.body);
          console.log("📩 Parsed Message:", parsedMessage);
          this.partyUpdates.next(parsedMessage); // Ensure values are emitted
        } catch (error) {
          console.error("🚨 Error Parsing WebSocket Message:", error);
        }
      });
    };

    this.stompClient.onStompError = (frame) => {
      console.error("❌ STOMP error:", frame);
    };

    this.stompClient.activate();
  }

  getPartyUpdates(): Observable<GameStart | null> {
    return this.partyUpdates.asObservable();
  }
}

