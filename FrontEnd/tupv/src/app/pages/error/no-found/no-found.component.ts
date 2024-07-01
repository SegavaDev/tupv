import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';



@Component({
  selector: 'app-no-found',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './no-found.component.html',
  styleUrl: './no-found.component.css'
})
export class NoFoundComponent {

    regresar() {
        history.back();
    }

}
