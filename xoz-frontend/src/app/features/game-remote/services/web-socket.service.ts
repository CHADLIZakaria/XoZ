import { Injectable } from '@angular/core';
import { Client, IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { GameResult, GameStart, Move } from 'src/app/models/game';

@Injectable()
export class WebSocketService {
  private stompClient!: Client;
  private messageSubject = new Subject<GameStart>();
  private movesSubject = new Subject<Move[]>();
  
  private connectionStatus$ = new BehaviorSubject<boolean>(false);
  private isConnected = false;
  private isSubscribed = false;

  constructor() {
    this.initWebsocket();
  }

  initWebsocket() {
    this.stompClient = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      debug: (str) => console.log('STOMP Debug:', str),

      onConnect: () => {
        console.log('WebSocket connected ✅');
        this.isConnected = true;
        this.connectionStatus$.next(true);

        if (!this.isSubscribed) {
          this.subscribeToPartyTopic();
          this.subscribeToMoveTopic()
        }
      },

      onDisconnect: () => {
        console.log('WebSocket disconnected ❌');
        this.isConnected = false;
        this.isSubscribed = false;
        this.connectionStatus$.next(false);
      },

      onStompError: (frame) => {
        console.error('STOMP Error:', frame);
      }
    });
    this.stompClient.activate();
  }

  private subscribeToPartyTopic(): void {
    this.stompClient.subscribe('/topic/party-topic', (message: IMessage) => {
      console.log('Received message:', message.body);
      try {
        const gameStart: GameStart = JSON.parse(message.body);
        this.messageSubject.next(gameStart);
      } catch (error) {
        console.error('Error parsing message:', error);
      }
    });
    this.isSubscribed = true;
  }

  private subscribeToMoveTopic(): void {
    this.stompClient.subscribe('/topic/move-topic', (message: IMessage) => {
      console.log('Received message:', message.body);
      try {
        const gameStart: Move[] = JSON.parse(message.body);
        this.movesSubject.next(gameStart);
      } catch (error) {
        console.error('Error parsing message:', error);
      }
    });
  }

  onParty(): Observable<GameStart> {
    return this.messageSubject.asObservable();
  }

  onMoves(): Observable<Move[]> {
    return this.movesSubject.asObservable();
  }

  getConnectionStatus(): Observable<boolean> {
    return this.connectionStatus$.asObservable();
  }

  disconnect(): void {
    if (this.stompClient && this.stompClient.active) {
      this.stompClient.deactivate();
      this.isConnected = false;
      this.isSubscribed = false;
      this.connectionStatus$.next(false);
    }
  }
}
