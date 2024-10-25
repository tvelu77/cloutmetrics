import { Component } from '@angular/core';
import { Git } from '../git/git';
import { GitService } from '../git/git.service';
import { MatTableModule } from '@angular/material/table';

@Component({
  selector: 'app-git-list',
  standalone: true,
  imports: [MatTableModule],
  templateUrl: './git-list.component.html',
  styleUrl: './git-list.component.scss'
})
export class GitListComponent {
  displayedColumns: string[] = ['id', 'name', 'url', 'date'];
  gits: Git[] = [];
  loading = true;

  constructor(private readonly gitService: GitService) {
    gitService.findAll()
    .subscribe((response) => {
      this.gits = response;
      this.loading = false;
    });
  }

}
