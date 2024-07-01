import { Routes } from '@angular/router';
import { NoFoundComponent } from './pages/error/no-found/no-found.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        loadChildren: () => import('./pages/home/home.routes').then(r => r.HOME_ROUTES),
    },
    {
        path: '404',
        component: NoFoundComponent,
        data: {
            title: 'TuPV | 404',
            favicon: 'alerta_rojo.svg'
        }
    },
    {
        path: '**',
        component: NoFoundComponent,
        data: {
            title: 'TuPV | 404',
            favicon: 'alerta_rojo.svg'
        }
    }
];
