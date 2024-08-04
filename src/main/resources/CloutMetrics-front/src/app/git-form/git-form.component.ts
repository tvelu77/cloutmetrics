import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GitService } from '../service/git-service.service';
import { Git } from '../model/git';

@Component({
  selector: 'app-git-form',
  templateUrl: './git-form.component.html',
  styleUrls: ['./git-form.component.css']
})
export class GitFormComponent {

  git: Git;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private gitService: GitService) {
    this.git = new Git();
  }

  onSubmit() {
    this.gitService.save(this.git).subscribe(result => this.gotoGitList());
  }

  gotoGitList() {
    this.router.navigate(['/gits']);
  }
}
