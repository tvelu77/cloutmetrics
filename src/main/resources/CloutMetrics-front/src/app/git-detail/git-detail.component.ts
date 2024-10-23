import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Git } from '../model/git';
import { DetailedGit } from '../model/detailedgit';
import { GitService } from '../service/git-service.service';

@Component({
  selector: 'app-git-detail',
  templateUrl: './git-detail.component.html',
  styleUrls: ['./git-detail.component.css']
})
export class GitDetailComponent implements OnInit {

  git!: Git;
  detailedgit!: DetailedGit;

  constructor(
    private route: ActivatedRoute,
    private gitService: GitService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getGit();
  }

  getGit(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.gitService.getGit(id)
      .subscribe(git => this.detailedgit = git);
  }

}
