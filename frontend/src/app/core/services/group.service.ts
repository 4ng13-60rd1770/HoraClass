import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { GroupRequest, GroupResponse } from '../models/course.models';

@Injectable({ providedIn: 'root' })
export class GroupService {
  private adminUrl = `${environment.apiUrl}/admin/grupos`;
  private publicUrl = `${environment.apiUrl}/grupos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<GroupResponse[]> {
    return this.http.get<GroupResponse[]>(`${this.publicUrl}/all`);
  }

  getById(id: string): Observable<GroupResponse> {
    return this.http.get<GroupResponse>(`${this.publicUrl}/${id}`);
  }

  create(group: GroupRequest): Observable<GroupResponse> {
    return this.http.post<GroupResponse>(`${this.adminUrl}/create`, group);
  }

  update(group: GroupRequest): Observable<GroupResponse> {
    return this.http.put<GroupResponse>(this.adminUrl, group);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/${id}`);
  }
}
