import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Git } from './git';
import { GitService } from './git.service';
import { MenuItem } from 'primeng/api';
import { TabViewChangeEvent, TabViewModule } from 'primeng/tabview';
import { DatePipe } from '@angular/common';
import { AccordionModule } from 'primeng/accordion';

@Component({
  selector: 'app-git',
  standalone: true,
  imports: [TabViewModule, DatePipe, AccordionModule, RouterLink, RouterLinkActive],
  templateUrl: './git.component.html',
  styleUrl: './git.component.scss'
})
export class GitComponent {
  git!: Git;
  loading = true;
  items!: MenuItem[];
  activeItem!: MenuItem;
  constructor(private readonly route: ActivatedRoute, private readonly gitService: GitService, private readonly router: Router) {
    this.items = [
      {
        label: 'Overview',
        icon: 'pi pi-eye'
      },
      {
        label: 'Metrics',
        icon: 'pi pi-chart-pie'
      },
      {
        label: 'Manage',
        icon: 'pi pi-cog'
      }
    ];
    this.activeItem = this.items[0];
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

  handleChange(event: TabViewChangeEvent) {
    if (event.index === 3) {
      this.router.navigate(['']);
    }
  }



}
