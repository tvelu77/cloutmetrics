import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Git } from './git';
import { GitService } from './git.service';
import { MenuItem, MessageService } from 'primeng/api';
import { TabViewChangeEvent, TabViewModule } from 'primeng/tabview';
import { DatePipe } from '@angular/common';
import { TagModule } from 'primeng/tag';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-git',
  standalone: true,
  imports: [TabViewModule, DatePipe, TagModule, RouterLink, RouterLinkActive, ReactiveFormsModule, ButtonModule, ToastModule],
  templateUrl: './git.component.html',
  styleUrl: './git.component.scss',
  providers: [MessageService]
})
export class GitComponent {
  git!: Git;
  loading = true;
  items!: MenuItem[];
  activeItem!: MenuItem;
  renameForm = new FormGroup({
    name: new FormControl('', Validators.required),
  });
  deleteForm = new FormGroup({});
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



}
