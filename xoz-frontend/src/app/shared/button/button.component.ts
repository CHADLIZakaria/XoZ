import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [],
  templateUrl: './button.component.html',
  styleUrl: './button.component.less'
})
export class ButtonComponent {
  text = input.required<string>();
  icon = input<string>();
  classButton = input<string>();

  onClick = output<void>();

  notify() {
    this.onClick.emit();
  } 
}
