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
}
