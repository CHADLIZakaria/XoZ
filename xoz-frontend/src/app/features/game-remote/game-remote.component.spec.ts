import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GameRemoteComponent } from './game-remote.component';

describe('GameRemoteComponent', () => {
  let component: GameRemoteComponent;
  let fixture: ComponentFixture<GameRemoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GameRemoteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GameRemoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
