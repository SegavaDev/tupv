import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { FooterComponent } from '../../components/footer/footer.component';
import { NavHomeComponent } from '../../components/nav-home/nav-home.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, RouterLink, FooterComponent, NavHomeComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

  imagenes: string[] = [
    "assets/imagen1.png",
    "assets/imagen2.png",
    "assets/imagen3.png",
    "assets/imagen4.png"
  ];

  mostrarZoomImg(index: number) {
    const img: string = this.imagenes[index];
    this.imagenes.splice(index, 1);
    this.imagenes = [
      img, ...this.imagenes
    ];
  }
}
