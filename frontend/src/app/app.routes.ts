import { Routes } from '@angular/router';
import { GitListComponent } from './git-list/git-list.component';

export const API_ROUTE = 'http://localhost:8080';

export const routes: Routes = [
    {
        path: '',
        component: GitListComponent
    }
];
