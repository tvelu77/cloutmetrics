import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Git, GitStatus } from './git';
import { GitService } from './git.service';
import { MenuItem, MessageService } from 'primeng/api';
import { TabViewChangeEvent, TabViewModule } from 'primeng/tabview';
import { DatePipe, KeyValuePipe, NgFor } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ChartModule } from 'primeng/chart';
import { SkeletonModule } from 'primeng/skeleton';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { DialogModule } from 'primeng/dialog';

@Component({
  selector: 'app-git',
  standalone: true,
  imports: [TabViewModule,
    DatePipe,
    TagModule,
    RouterLink,
    RouterLinkActive,
    ReactiveFormsModule,
    ButtonModule,
    ToastModule,
    ChartModule,
    NgFor,
    KeyValuePipe,
    SkeletonModule,
    ProgressSpinnerModule,
    DialogModule],
  templateUrl: './git.component.html',
  styleUrl: './git.component.scss',
  providers: [MessageService]
})
export class GitComponent {
  git!: Git;
  gitStatus = GitStatus;
  loading = true;
  items!: MenuItem[];
  activeItem!: MenuItem;
  renameForm = new FormGroup({
    name: new FormControl('', Validators.required),
  });
  deleteForm = new FormGroup({});
  data!: any;
  options!: any;
  visible = false;
  constructor(private readonly route: ActivatedRoute,
    private readonly gitService: GitService,
    private readonly router: Router,
    private readonly messageService: MessageService) 
    {
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
      const data = Object.values(this.git.metrics?.languageAndFileCount!);
      const colors = this.getColors(data.length);
      this.data = {
        labels: Object.keys(this.git.metrics?.languageAndFileCount!),
        datasets: [
          {
            label: 'Number of files',
            data: data,
            backgroundColor: colors,
            hoverBackgroundColor: colors.map(color => this.lightenColor(color, 20)),
            borderWidth: 1
          }
        ]
      };
      this.options = {
        cutout: '60%',
      };
    });
  }

  handleChange(event: TabViewChangeEvent) {
    if (event.index === 3) {
      this.router.navigate(['']);
    }
  }

  rename() {
    const oldGitName = this.git.name;
    this.git.name = this.renameForm.value.name!;
    this.gitService.update(this.git, this.git.id!).subscribe((response) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: oldGitName + " has been successfully renamed to " + this.git.name });
    });
  }

  delete() {
    this.gitService.delete(this.git.id!).subscribe((response) => {
      this.router.navigate(['']);
    });
  }

  private getColors(length: number): string[] {
    const colors = [];
    for (let i = 0; i < length; i++) {
      colors.push(this.getRandomColor());
    }
    return colors;
  }

  private getRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }

  private lightenColor(color: string, percent: number): string {
    let num = parseInt(color.slice(1), 16);
    let amt = Math.round(2.55 * percent);
    let R = (num >> 16) + amt;
    let G = (num >> 8 & 0x00FF) + amt;
    let B = (num & 0x0000FF) + amt;
    return `#${(0x1000000 + (R < 255 ? R < 1 ? 0 : R : 255) * 0x10000 + (G < 255 ? G < 1 ? 0 : G : 255) * 0x100 + (B < 255 ? B < 1 ? 0 : B : 255)).toString(16).slice(1)}`;
  }



}
