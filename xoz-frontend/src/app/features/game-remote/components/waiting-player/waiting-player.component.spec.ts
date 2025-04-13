import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WaitingPlayerComponent } from './waiting-player.component';

describe('WaitingPlayerComponent', () => {
  let component: WaitingPlayerComponent;
  let fixture: ComponentFixture<WaitingPlayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WaitingPlayerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WaitingPlayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
