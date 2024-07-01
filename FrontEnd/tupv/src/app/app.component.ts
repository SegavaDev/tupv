import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { FooterComponent } from './components/footer/footer.component';
import { Title } from '@angular/platform-browser';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

    //* Se configura la automatización de actualización de títulos e iconos en pestañas
    defaultFaviconUrl: string = 'assets/caja_registradora_gris.svg';
    faviconUrl: string = this.defaultFaviconUrl;

    constructor(
        private titleService: Title,
        private router: Router
    ) {}

    ngOnInit() {
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd)
        ).subscribe(() => {
            const title = this.getTitle(this.router.routerState.snapshot.root).join(' - ');
            this.titleService.setTitle(title);

            const favicon = this.getFavicon(this.router.routerState.snapshot.root) || this.defaultFaviconUrl;
            this.updateFavicon(favicon);
        });
    }

    getTitle(route: any, parent: any = []): string[] {
        const data = [...parent];
        if (route.data && route.data.title) {
            data.push(route.data.title);
        }

        if (route.firstChild) {
            return this.getTitle(route.firstChild, data);
        }

        return data;
    }

    getFavicon(route: any): string | null {
        if (route.data && route.data.favicon) {
            return route.data.favicon;
        }

        if (route.firstChild) {
            return this.getFavicon(route.firstChild);
        }

        return null;
    }

    updateFavicon(faviconUrl: string) {
        let link: HTMLLinkElement | null = document.querySelector('link[rel="icon"]');

        if (!link) {
            link = document.createElement('link');
            link.rel = 'icon';
            document.head.appendChild(link);
        }

        link.href = faviconUrl;
    }
}
