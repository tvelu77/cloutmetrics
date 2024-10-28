import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GitService } from '../git/git.service';
import { Git } from '../git/git';

@Component({
  selector: 'app-git-add',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './git-add.component.html',
  styleUrl: './git-add.component.scss'
})
export class GitAddComponent {
  gitAddForm = new FormGroup({
    url: new FormControl('', Validators.required),
  });

  constructor(private readonly gitService: GitService) {}

  onSubmit() {
    const newGit: Git = {
      url: this.gitAddForm.value.url!,
      name: 'test'
    }
    this.gitService.add(newGit).subscribe(git => {
      console.log("hey");
    })

  }
}
