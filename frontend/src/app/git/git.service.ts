import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Git } from './git';
import { API_ROUTE } from '../app.routes';

@Injectable({
  providedIn: 'root'
})
export class GitService {

  constructor(private readonly http: HttpClient) { }

  findAll(): Observable<Git[]> {
    return this.http.get<Git[]>(API_ROUTE + '/gits');
  }

  findById(id: number): Observable<Git> {
    return this.http.get<Git>(API_ROUTE + '/gits/' + id);
  }

  add(git: Git): Observable<Git> {
    return this.http.post<Git>(API_ROUTE + '/gits', git);
  }

  delete(id: number): Observable<Git> {
    return this.http.delete<Git>(API_ROUTE + '/gits/' + id);
  }

  update(git: Git, id: number): Observable<Git> {
    return this.http.put<Git>(API_ROUTE + '/gits/' + id, git);
  }

  compute(id: number): Observable<Git> {
    return this.http.put<Git>(API_ROUTE + '/gits/' + id + '/compute', {});
  }
}
