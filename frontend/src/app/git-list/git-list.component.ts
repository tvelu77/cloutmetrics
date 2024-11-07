import { Component } from '@angular/core';
import { Git, GitStatus } from '../git/git';
import { GitService } from '../git/git.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { DatePipe, NgFor } from '@angular/common';
import { TableModule, TableRowSelectEvent } from 'primeng/table';
import { Tag, TagModule } from 'primeng/tag';

@Component({
  selector: 'app-git-list',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, DatePipe, TableModule, NgFor, TagModule],
  templateUrl: './git-list.component.html',
  styleUrl: './git-list.component.scss'
})
export class GitListComponent {
  displayedColumns: string[] = ['id', 'name', 'url', 'date', 'actions', 'status'];
  gits: Git[] = [];
  selectedGit!: Git;
  loading = true;

  constructor(private readonly gitService: GitService, private readonly router: Router) {
    gitService.findAll()
    .subscribe((response) => {
      this.gits = response;
      this.loading = false;
    });
  }

  handleSelect(event: TableRowSelectEvent) {
    this.router.navigate([event.data?.id]);
  }

  getSeverity(status: GitStatus): Tag["severity"] {
    switch (status) {
        case GitStatus.FINISHED:
            return 'success';
        case GitStatus.IN_PROGESS:
            return 'warning';
        case GitStatus.ERROR:
            return 'danger';
        default:
            return 'info';
    }
}

}
