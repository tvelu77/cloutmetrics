import { Component } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GitService } from '../git/git.service';
import { Git } from '../git/git';
import { ButtonModule } from 'primeng/button';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-git-add',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, ButtonModule, RouterLink, RouterLinkActive],
  templateUrl: './git-add.component.html',
  styleUrl: './git-add.component.scss'
})
export class GitAddComponent {
  gitAddForm = new FormGroup({
    url: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
  });

  constructor(private readonly gitService: GitService, private readonly router: Router) {}

  onSubmit() {
    const newGit: Git = {
      url: this.gitAddForm.value.url!,
      name: this.gitAddForm.value.name!
    }
    this.gitService.add(newGit).subscribe(git => {
      this.router.navigate(['']);
    })
  }
}
