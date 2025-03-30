import { TestBed } from '@angular/core/testing';

import { GameRemoteService } from './game-remote.service';

describe('GameRemoteService', () => {
  let service: GameRemoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GameRemoteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
