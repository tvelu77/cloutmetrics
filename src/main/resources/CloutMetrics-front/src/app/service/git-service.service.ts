import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Git } from '../model/git';
import { DetailedGit } from '../model/detailedgit';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class GitService {

  private gitUrl: string;

  constructor(private http: HttpClient) {
    this.gitUrl = 'http://localhost:8080/gits';
  }

  public findAll(): Observable<Git[]> {
    return this.http.get<Git[]>(this.gitUrl);
  }

  public getGit(id: number): Observable<DetailedGit> {
    const url = `${this.gitUrl}/${id}`;
    return this.http.get<DetailedGit>(url);
  }

  public save(git: Git) {
    return this.http.post<Git>(this.gitUrl, git);
  }
}
