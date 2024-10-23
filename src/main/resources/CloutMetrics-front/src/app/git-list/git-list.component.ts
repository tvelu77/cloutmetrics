import { Component, OnInit } from '@angular/core';
import { Git } from '../model/git';
import { GitService } from '../service/git-service.service';

@Component({
  selector: 'app-git-list',
  templateUrl: './git-list.component.html',
  styleUrls: ['./git-list.component.css']
})
export class GitListComponent implements OnInit {

  gits!: Git[];

  constructor(private gitService: GitService) {
  }

  ngOnInit() {
    this.gitService.findAll().subscribe(data => {
      this.gits = data;
    });
  }
}
