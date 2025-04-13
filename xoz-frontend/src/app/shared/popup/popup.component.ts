import { Component, input, output } from '@angular/core';
import { ButtonComponent } from "../button/button.component";
import { animate, group, query, style, transition, trigger } from '@angular/animations';

@Component({
    selector: 'app-popup',
    standalone: true,
    imports: [ButtonComponent],
    templateUrl: './popup.component.html',
    styleUrl: './popup.component.less',
    animations: [
        trigger('modalClose', [
            transition(':enter', [
                group([
                    query('.modal-overlay', [
                        style({ opacity: 0.5 }),
                        animate('500ms', style({ opacity: 1 })),
                    ]),
                    query('.modal-content', [
                        style({ opacity: 0.3, scale: 0.8 }),
                        animate('500ms', style({ opacity: 1, scale: 1 })),
                    ]),
                ]),
            ]),
            transition(':leave', [
                group([
                    query('.modal-overlay', [
                        style({ opacity: 1 }),
                        animate('500ms', style({ opacity: 0.5 })),
                    ]),
                    query('.modal-content', [
                        style({ opacity: 1, scale: 1 }),
                        animate('500ms', style({ opacity: 0.3, scale: 0.8 })),
                    ]),
                ]),
            ])
        ])
    ]
})
export class PopupComponent {
  close = output<void>();
  isOpen = input.required<boolean>();
  message = input<string>();
  restart = output<void>();
  icon = input<string>();
  
  onClose() {
    this.close.emit()
  }

  onRestart() {
    this.close.emit()
    this.restart.emit()
  }

}
