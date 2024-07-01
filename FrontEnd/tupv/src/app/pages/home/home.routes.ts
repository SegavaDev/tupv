import { Routes } from "@angular/router";
import { HomeComponent } from "./home.component";

export const HOME_ROUTES: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: {
            title: 'Tupv | Home',
            favicon: 'caja_registradora_gris.svg'
        }
    }
]