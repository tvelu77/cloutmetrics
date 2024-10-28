import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Git } from './git';
import { GitService } from './git.service';

@Component({
  selector: 'app-git',
  standalone: true,
  imports: [],
  templateUrl: './git.component.html',
  styleUrl: './git.component.scss'
})
export class GitComponent {
  git!: Git;
  loading = true;
  constructor(private readonly route: ActivatedRoute, private readonly gitService: GitService) {

  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      return;
    }
    this.gitService.findById(+id).subscribe((response) => {
      this.git = response;
      this.loading = false;
    });
  }

}
