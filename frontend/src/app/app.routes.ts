import { Routes } from '@angular/router';
import { GitListComponent } from './git-list/git-list.component';
import { GitComponent } from './git/git.component';
import { GitAddComponent } from './git-add/git-add.component';

export const API_ROUTE = 'http://localhost:8080';

export const routes: Routes = [
    {
        path: '',
        component: GitListComponent
    },
    {
        path: 'add',
        component: GitAddComponent
    },
    {
        path: ':id',
        component: GitComponent
    }
];
