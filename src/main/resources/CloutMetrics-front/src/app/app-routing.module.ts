import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GitListComponent } from './git-list/git-list.component';
import { GitFormComponent } from './git-form/git-form.component';
import { GitDetailComponent } from './git-detail/git-detail.component';

const routes: Routes = [
  { path: 'gits', component: GitListComponent },
  { path: 'addgit', component: GitFormComponent },
  { path: 'gits/:id', component: GitDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
