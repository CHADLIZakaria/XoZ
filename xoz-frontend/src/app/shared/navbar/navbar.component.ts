import { Component } from '@angular/core';
import { ButtonComponent } from '../button/button.component';

@Component({
    selector: 'app-navbar',
    imports: [ButtonComponent],
    templateUrl: './navbar.component.html',
    styleUrl: './navbar.component.less'
})
export class NavbarComponent {

}
